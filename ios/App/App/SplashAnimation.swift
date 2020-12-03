//
//  SplashAnimation.swift
//
//  Created by Aparajita on 10/16/20.
//

import Capacitor

private let kLogoTag = 1
private let kDefaultDuration: Double = 0.7 // seconds
private var originalTransform = CGAffineTransform()

typealias Callback = () -> Void

enum EventType: String {
  case animate
  case beforeShow
  case afterShow
}

extension AppDelegate {
  @objc func onSplashScreenEvent(_ event: String, _ params: Any) {
    guard let params = params as? [String: Any],
          let eventType = EventType(rawValue: event) else {
      return
    }

    switch eventType {
    case .animate:
      animate(withParams: params)

    case .beforeShow:
      handleBeforeShow(params: params)

    case .afterShow:
      handleAfterShow(params: params)
    }
  }

  private func animate(withParams params: [String: Any]) {
    guard let view = params["splashView"] as? UIView,
          let options = params["options"] as? [String: Any],
          let done = params["done"] as? Callback else {
      return
    }

    let spinner = params["spinner"] as? UIActivityIndicatorView
    let duration = options["duration"] as? Double ?? kDefaultDuration
    let delay = options["delay"] as? TimeInterval ?? 0.0

    UIView.animateKeyframes(
      withDuration: duration,
      delay: delay,
      options: [],
      animations: {
        self.performAnimation(forView: view, spinner: spinner)
      },
      completion: { _ in
        self.finishAnimation(forView: view, spinner: spinner, done: done)
      }
    )
  }

  private func performAnimation(forView view: UIView, spinner: UIActivityIndicatorView?) {
    guard let logo = view.viewWithTag(kLogoTag) else {
      return
    }

    originalTransform = logo.transform
    let keyFrames: [(Double, Double, () -> Void)] = [
      (
        0, 0.3,
        { logo.transform = originalTransform.scaledBy(x: 0.9, y: 0.9) }
      ),
      (
        0.3, 0.7,
        { logo.transform = originalTransform.scaledBy(x: 4, y: 4) }
      ),
      (
        0.8, 0.2,
        {
          view.alpha = 0
          spinner?.alpha = 0
        }
      )
    ]

    for (startTime, duration, animations) in keyFrames {
      UIView.addKeyframe(
        withRelativeStartTime: startTime,
        relativeDuration: duration,
        animations: animations
      )
    }
  }

  private func finishAnimation(forView view: UIView, spinner: UIActivityIndicatorView?, done: Callback) {
    if let logo = view.viewWithTag(kLogoTag) {
      logo.transform = originalTransform
    }

    done()
  }

  private func handleBeforeShow(params: [String: Any]) {
    CAPLog.print("before show")
  }

  private func handleAfterShow(params: [String: Any]) {
    CAPLog.print("after show")
  }
}
