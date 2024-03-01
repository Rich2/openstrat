/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait NotSubTypeOf[A, B]

object NotSubTypeOf
{ implicit infix def isSub[A, B]: NotSubTypeOf[A, B] = null
  implicit def iSubAmbig1[A, B >: A]: NotSubTypeOf[A, B] = null
  implicit def iSubAmbig2[A, B >: A]: NotSubTypeOf[A, B] = null
}