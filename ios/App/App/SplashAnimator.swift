//
//  SplashAnimation.swift
//
//  Created by Aparajita on 10/16/20.
//

import AparajitaCapacitorSplashScreen

private let kLogoTag = 1
private let kDefaultDuration: Double = 0.7 // seconds
private let kDefaultFadeOutDuration: Double = 0.3 // seconds
private var originalTransform = CGAffineTransform()

extension AppDelegate {
  @objc func onSplashScreenEvent(_ event: String, _ params: [AnyHashable: Any]) {
    animate(withParams: params)
  }

  private func animate(withParams params: [AnyHashable: Any]) {
    logger.debug("animate")

    guard let view = params["splashView"] as? UIView,
          let options = params["options"] as? Config.CallOptions,
          let callbacks = params["callbacks"] as? SplashScreen.AnimationCallbacks else {
      return
    }

    // Just to prove you can pass arbitrary data in options
    logger.debug("foo = \(String(describing: options["foo"]))")
    let animationDuration = SplashScreen.toSeconds(
      Config.getDouble("animationDuration", inOptions: options)
      ?? kDefaultDuration)

    UIView.animateKeyframes(
      withDuration: animationDuration,
      delay: 0,
      options: [],
      animations: {
        self.performAnimation(forView: view)
      },
      completion: { _ in
        self.finishAnimation(forView: view, callbacks: callbacks)
      }
    )
  }

  private func performAnimation(forView view: UIView) {
    guard let logo = view.viewWithTag(kLogoTag) else {
      return
    }

    originalTransform = logo.transform

    // parameters: (relative delay, relative duration, closure expression)
    // swiftlint:disable:next large_tuple
    let keyFrames: [(Double, Double, () -> Void)] = [
      (0, 0.3, { logo.transform = originalTransform.scaledBy(x: 0.7, y: 0.7) }),
      (0.3, 0.7, { logo.transform = originalTransform.scaledBy(x: 5, y: 5) }),
      (0.3, 0.5, { view.alpha = 0 })
    ]

    for (startTime, duration, animations) in keyFrames {
      UIView.addKeyframe(
        withRelativeStartTime: startTime,
        relativeDuration: duration,
        animations: animations
      )
    }
  }

  private func finishAnimation(
    forView view: UIView,
    callbacks: SplashScreen.AnimationCallbacks) {
    if let logo = view.viewWithTag(kLogoTag) {
      logo.transform = CGAffineTransform(scaleX: 1.0, y: 1.0)
    }

    callbacks.done()
  }
}
