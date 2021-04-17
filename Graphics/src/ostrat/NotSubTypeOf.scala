/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait NotSubTypeOf[A, B]

object NotSubTypeOf
{ implicit def isSub[A, B]: A NotSubTypeOf B = null
  implicit def iSubAmbig1[A, B >: A]: A NotSubTypeOf B = null
  implicit def iSubAmbig2[A, B >: A]: A NotSubTypeOf B = null
}