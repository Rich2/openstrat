/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import scala.collection.immutable.ArraySeq
import scala.reflect.ClassTag

object Arr
{
  @inline def apply[A](inp: A *)(implicit ct: ClassTag[A]): Arr[A] = ArraySeq.apply(inp: _*)
  @inline def unapplySeq[A](x: ArraySeq[A]): collection.SeqFactory.UnapplySeqWrapper[A] = ArraySeq.unapplySeq(x)
}
