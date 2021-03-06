/*                     __                                               *\
**     ________ ___   / /  ___     jms-consumer                         **
**    / __/ __// _ | / /  / _ |    Copyright (C) 2017                   **
**  __\ \/ /__/ __ |/ /__/ __ |                                         **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package org.jms.consumer

import javax.jms.{Message, TextMessage}

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

import scala.util.{Failure, Success, Try}
import scalaz.effect.IO

/**
  * Subscribes to a [[MsgFlowable]] and handles the items it emits and any error notification it issues.
  *
  * Following objects use this trait:
  * [[TextSubscriber]]
  *
  */
trait MsgSubscriber {
  /** Type of JMS message */
  type M <: Message

  /** Type of Scala object used to represent JMS message */
  type D

  /**
    * Subscribes to [[MsgFlowable]].
    *
    * @return a disposable resource.
    */
  def subscribe(): Disposable
}

/**
  * Handles JMS messages. Subscribes to a [[MsgFlowable]] to receive JMS messages.
  * JMS messages are converted to [[String]] objects before further processing.
  *
  * @constructor Creates new Text subscriber.
  * @param flowable  message flowable.
  * @param converter message converter
  * @param processor message processor.
  */
class TextSubscriber(
                      flowable: MsgFlowable,
                      converter: MsgConverter[String],
                      processor: MsgProcessor[String])
  extends MsgSubscriber {

  type M = TextMessage
  type D = String

  override def subscribe(): Disposable = {
    val onNext: Consumer[Message] =
      msg => {
        Try {
          val text = converter fromMessage msg.asInstanceOf[TextMessage]
          val action: IO[Unit] = processor process text

          action.unsafePerformIO()
        } match {
          case Success(_) =>
          case Failure(e) =>
            e.printStackTrace()
        }
      }

    flowable.messageFlowable().subscribe(onNext)
  }
}
