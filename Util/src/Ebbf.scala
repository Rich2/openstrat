/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Biased bifunctor for errors. */
sealed trait Ebbf[E <: Throwable, A]
{ /** Classic map function as we see on a Scala [[Option]] or [[List]]. */
  def map[B](f: A => B): Ebbf[E, B]

  /** Classic flatMap function as we see on a Scala [[Option]]. */
  def flatMap[B](f: A => Ebbf[E, B]): Ebbf[E, B]
}

/** Success, boxes a good value of the desired type. */
class Succ[E <: Throwable, A](value: A) extends Ebbf[E, A]
{ override def map[B](f: A => B): Ebbf[E, B] = new Succ[E, B](f(value))

  override def flatMap[B](f: A => Ebbf[E, B]): Ebbf[E, B] = f(value) match
  { case succ: Succ[E, B] => succ
    case fail: Fail[E, B] => fail
  }
}

/** Failure to return a value of the desired type. Boxes a [[Throwable]] error. */
class Fail[E <: Throwable, A](val error: E) extends Ebbf[E, A]
{ override def map[B](f: A => B): Ebbf[E, B] = new Fail[E, B](error)
  override def flatMap[B](f: A => Ebbf[E, B]): Ebbf[E, B] = new Fail[E, B](error)
}
