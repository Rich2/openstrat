package ostrat
import utest._, collection.mutable.ArrayBuffer

object ArrProdHomoTest  extends TestSuite
{
  case class Mine(a: Double, b: Double) extends ProdDbl2
  { def _1: Double = a
    def _2: Double = b
    override def canEqual(that: Any): Boolean = that match
    { case Mine(_, _) => true
      case _ => false
    }
  }

  object Mine
  {
    implicit val arrBuilderImplicit = new ArrProdDbl2Build[Mine, Mines]
    { type BuffT = MinesBuff
      override def fromDblArray(array: Array[Double]): Mines = new Mines(array)
      def fromDblBuffer(inp: ArrayBuffer[Double]): MinesBuff = new MinesBuff(inp)
    }
  }

  class Mines(val array: Array[Double]) extends AnyVal with ArrProdDbl2[Mine]
  { type ThisT = Mines
    def typeStr = "Mines"
    def unsafeFromArray(array: Array[Double]): Mines = new Mines(array)
    override def elemBuilder(d1: Double, d2: Double): Mine = Mine(d1, d2)
  }

  object Mines extends ProdDbl2sCompanion[Mine, Mines]
  {
    implicit val flatImplicit: ArrArrBuild[Mines] = Mine.arrBuilderImplicit

    //implicit val factory: Int => Mines = i => new Mines(new Array[Double](i * 2))
    implicit val bindImplicit: ArrFlatBuild[Mines] = new ArrFlatBuild[Mines]
    {
      override def flatMap[A](orig: ArrayLike[A], f: A => Mines): Mines =
      { val buff = new ArrayBuffer[Double]
        orig.foreach(a => buff.addAll(f(a).array))
        new Mines(buff.toArray)
      }
    }

    implicit val persistImplicit: ArrProdDbl2Persist[Mine, Mines] = new ArrProdDbl2Persist[Mine, Mines]("Mines")
    { override def fromArray(value: Array[Double]): Mines = new Mines(value)
    }
  }

  class MinesBuff(val buffer: ArrayBuffer[Double]) extends AnyVal with BuffProdDbl2[Mine]
  {// override def apply(index: Int): Mine = ??? // buffer(index)
  }

  val tests = Tests
  {
    val dbls1 = Dbls(1, 2, 3, 4)
    //val ds1 = dbls1.str
    val mines1 = dbls1.map(d => Mine(d, d * 2))
    val mines2 = dbls1.flatMap(d => Mines(Mine(d, d + 0.5), Mine(d * 2, d * 2)))
    val str1 = mines2.str

    "test1" -
      { dbls1(3) ==> 4
        mines1(3) ==> Mine(4, 8)
        mines2(0) ==> Mine(1, 1.5)
        Mine(3, 4).isInstanceOf[AnyRef] ==> true
        str1 ==> "Mines(1.0, 1.5; 2.0, 2.0; 2.0, 2.5; 4.0, 4.0; 3.0, 3.5; 6.0, 6.0; 4.0, 4.5; 8.0, 8.0)"
      }
  }
}
