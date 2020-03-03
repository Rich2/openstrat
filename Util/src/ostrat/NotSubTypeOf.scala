package ostrat

trait NotSubTypeOf[A, B]

object NotSubTypeOf
{ implicit def isSub[A, B]: A NotSubTypeOf B = null
  implicit def iSubAmbig1[A, B >: A]: A NotSubTypeOf B = null
  implicit def iSubAmbig2[A, B >: A]: A NotSubTypeOf B = null
}