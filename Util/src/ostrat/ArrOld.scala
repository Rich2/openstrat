/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import scala.collection.immutable.ArraySeq, reflect.ClassTag, collection.SeqFactory.UnapplySeqWrapper

object ArrOld
{ @inline def apply[A](inp: A *)(implicit ct: ClassTag[A]): ArrOld[A] = ArraySeq.apply(inp: _*)
  @inline def unapplySeq[A](arr: ArraySeq[A]): UnapplySeqWrapper[A] = ArraySeq.unapplySeq(arr)
}