/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

/** An example of a class that is based on the product of 2 [[Double]]s. This class, [[MyDbl2s]] and their companion objects show you the boiler
 *  plate necessary to create and use custom efficient flat Array based immutable collection classes. */
case class MyDbl2(a: Double, b: Double) extends ProdDbl2
{ def _1: Double = a
  def _2: Double = b
  override def canEqual(that: Any): Boolean = that match
  { case MyDbl2(_, _) => true
    case _ => false
  }
}

object MyDbl2
{
  implicit val arrBuilderImplicit = new ArrProdDbl2Build[MyDbl2, MyDbl2s]
  { type BuffT = MinesBuff
    override def fromDblArray(array: Array[Double]): MyDbl2s = new MyDbl2s(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): MinesBuff = new MinesBuff(inp)
  }
}

class MyDbl2s(val arrayUnsafe: Array[Double]) extends AnyVal with ArrProdDbl2[MyDbl2]
{ type ThisT = MyDbl2s
  def typeStr = "Mines"
  def unsafeFromArray(array: Array[Double]): MyDbl2s = new MyDbl2s(array)
  override def elemBuilder(d1: Double, d2: Double): MyDbl2 = MyDbl2(d1, d2)
  override def fElemStr: MyDbl2 => String = _.toString
}

object MyDbl2s extends ProdDbl2sCompanion[MyDbl2, MyDbl2s]
{
  implicit val flatImplicit: ArrFlatBuild[MyDbl2s] = MyDbl2.arrBuilderImplicit

  implicit val persistImplicit: ArrProdDbl2Persist[MyDbl2, MyDbl2s] = new ArrProdDbl2Persist[MyDbl2, MyDbl2s]("Mines")
  { override def fromArray(value: Array[Double]): MyDbl2s = new MyDbl2s(value)
  }
}

class MinesBuff(val buffer: ArrayBuffer[Double]) extends AnyVal with BuffProdDbl2[MyDbl2]
{ def dblsToT(d1: Double, d2: Double): MyDbl2 = MyDbl2(d1, d2)
}
