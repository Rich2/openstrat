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

  class Mines(val array: Array[Double]) extends AnyVal with ArrProdDbl2[Mine]
  { type ThisT = Mines
    def typeStr = "Mines"
    def unsafeFromArray(array: Array[Double]): Mines = new Mines(array)
    override def elemBuilder(d1: Double, d2: Double): Mine = Mine(d1, d2)
  }

  object Mines extends ProdDbl2sCompanion[Mine, Mines]
  {
    implicit val factory: Int => Mines = i => new Mines(new Array[Double](i * 2))
    implicit val bindImplicit: Bind[Mines] = new Bind[Mines] {
      override def bind[A](orig: ArrayLike[A], f: A => Mines): Mines =
      { val buff = new ArrayBuffer[Double]
        orig.foreach(a => buff.addAll(f(a).array))
        new Mines(buff.toArray)
      }
    }
  }

  val tests = Tests
  {
    val dbls1 = Dbls(1, 2, 3, 4)
    val mines1 = dbls1.map(d => Mine(d, d * 2))
    val mines2 = dbls1.bind(d => Mines(Mine(d, d), Mine(d * 2, d * 2)))
    'test1 -
      { dbls1(3) ==> 4
        mines1(3) ==> Mine(4, 8)
        mines2(0) ==> Mine(1, 1)
      }


  }
}
