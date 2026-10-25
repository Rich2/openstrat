/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance, reflect.ClassTag

sealed trait ELog[+E, +L, +A]
{ /** maps from type A to be B, preserving the log without adding to it */
  def map[B](f: A => B): ELog[E, L, B]

  /** maps from type A to be B, adding an entry to the log. */
  def logMap[LL >: L, B](f: A => (LL, B))(using ClassTag[LL]): ELog[E, LL, B]

  /** maps from type A to be B, optionally adding an entry to the log. */
  def optLogMap[LL >: L, B](f: A => (Option[LL], B))(using ClassTag[LL]): ELog[E, LL, B]
}

final class Succ[+L, +A](val log: RArr[L] @uncheckedVariance, val value: A) extends ELog[Nothing, L, A]
{ override def map[B](f: A => B): Succ[L, B] = Succ(log, f(value))

  override def logMap[LL >: L, B](f: A => (LL, B))(using ClassTag[LL]): Succ[LL, B] =
  { val (warn, newValue) = f(value)
    Succ(log +% warn, newValue)
  }

  override def optLogMap[LL >: L, B](f: A => (Option[LL], B))(using ClassTag[LL]): Succ[LL, B] =
  { val pair: (Option[LL], B) = f(value)
    val newLog: RArr[LL] = pair._1 match
    { case Some(l) => log +% l
      case _ => log
    }
    Succ(newLog, pair._2)
  }
}

object Succ
{
  def apply[L, A](log: RArr[L], value: A): Succ[L, A] = new Succ(log, value)
}

/** An [[ELog]] fail with an error and a log. */
class Fail[+E, +L](val error: E, val log: RArr[L]) extends ELog[E, L, Nothing]
{ override def map[B](f: Nothing => B): Fail[E, L] = this
  override def logMap[LL >: L, B](f: Nothing => (LL, B))(using ClassTag[LL]): Fail[E, LL] = this
  override def optLogMap[LL >: L, B](f: Nothing => (Option[LL], B))(using ClassTag[LL]): Fail[E, L] = this
}

object Fail
{
  def apply[E, L](error: E, log: RArr[L]): Fail[E, L] = new Fail(error, log)
}