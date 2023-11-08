/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

/** An example of a class that is based on the product of 2 [[Double]]s. This class, [[MyDbl2Arr]] and their companion objects show you the boiler
 *  plate necessary to create and use custom efficient flat Array based immutable collection classes. */
case class MyDbl2Elem(a: Double, b: Double) extends Dbl2Elem
{ override def dbl1: Double = a
  override def dbl2: Double = b
  override def canEqual(that: Any): Boolean = that match
  { case MyDbl2Elem(_, _) => true
    case _ => false
  }
}

object MyDbl2Elem
{
  implicit val showEv: ShowDbl2[MyDbl2Elem] = ShowDbl2[MyDbl2Elem]("MyDbl2", "a", _.a, "b", _.b)

  implicit val unshowEv: UnshowDbl2[MyDbl2Elem] = UnshowDbl2[MyDbl2Elem]("MyDbl2", "a", "b", MyDbl2Elem.apply)

  implicit val arrBuilderImplicit: BuilderArrDbl2Map[MyDbl2Elem, MyDbl2Arr] = new BuilderArrDbl2Map[MyDbl2Elem, MyDbl2Arr]
  { type BuffT = MinesBuff
    override def fromDblArray(array: Array[Double]): MyDbl2Arr = new MyDbl2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): MinesBuff = new MinesBuff(buffer)
  }
}

final class MyDbl2Arr(val unsafeArray: Array[Double]) extends AnyVal with ArrDbl2[MyDbl2Elem]
{ type ThisT = MyDbl2Arr
  def typeStr = "Mines"
  def fromArray(array: Array[Double]): MyDbl2Arr = new MyDbl2Arr(array)
  override def seqDefElem(d1: Double, d2: Double): MyDbl2Elem = MyDbl2Elem(d1, d2)
  override def fElemStr: MyDbl2Elem => String = _.toString
}

object MyDbl2Arr extends CompanionSeqLikeDbl2[MyDbl2Elem, MyDbl2Arr]
{
  implicit val flatImplicit: BuilderArrFlat[MyDbl2Arr] = new BuilderArrDbl2Flat[MyDbl2Arr]
  { type BuffT = MinesBuff
    override def fromDblArray(array: Array[Double]): MyDbl2Arr = new MyDbl2Arr(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): MinesBuff = new MinesBuff(inp)
  }

  override def fromArray(array: Array[Double]): MyDbl2Arr = new MyDbl2Arr(array)

  implicit lazy val showEv: ShowSequ[MyDbl2Elem, MyDbl2Arr] = ShowSequ[MyDbl2Elem, MyDbl2Arr]()

  implicit lazy val unshowEv: UnshowSequ[MyDbl2Elem, MyDbl2Arr] = UnshowSequ[MyDbl2Elem, MyDbl2Arr]()
}

class MinesBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[MyDbl2Elem]
{ override def typeStr: String = "MinesBuff"
  def newElem(d1: Double, d2: Double): MyDbl2Elem = MyDbl2Elem(d1, d2)
}