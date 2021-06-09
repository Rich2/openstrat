/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An Optional Either. The value can be an A a B or none. */
sealed trait OptEither[+A, +B]

/** An OptEither instantiation that has a value of type A, the first type parameter. */
case class SomeA[+A, +B](value: A) extends OptEither[A, B]

/** An OptEither instantiation that has a value of type B, the second type parameter. */
case class SomeB[+A, +B](value: B) extends OptEither[A, B]

/** An OptEither with a none value it has neither a value of type A or typeB,  */
case object NoOptEither extends OptEither[Nothing, Nothing]