/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** A base trait for [[Show4ing]] and [[Unshow4]], declares the common properties of name1 - 4 and opt1 - 4. */
trait PersistBase4Plus[A1, A2, A3, A4] extends Any with PersistBase3Plus[A1, A2, A3]
{ /** 4th parameter name. */
  def name4: String

  /** The optional default value for parameter 4. */
  def opt4: Option[A4]

  /** The declaration here allows the same field to be to cover [[Showing]][A4] [[UnShow]][A4] and [[Persist]][A4]. */
  def persist4: Showing[A4] | Unshow[A4]
}

trait PersistBase4[A1, A2, A3, A4] extends Any with PersistBase4Plus[A1, A2, A3, A4]
{ override def paramNames: StrArr = StrArr(name1, name2, name3, name4)
  override def numParams: Int = 4
}

/** [[Showed]] trait for classes with 4+ Show parameters. */
trait Show4Plused[A1, A2, A3, A4] extends Any with Show3Plused[A1, A2, A3] with PersistBase4Plus[A1, A2, A3, A4]
{ /** The optional default value for parameter 4. */
  override def opt4: Option[A4] = None

  /** Element 4 of this 4+ element Show product. */
  def show4: A4

  override def persist4: Showing[A4]
}

/** Trait for [[ShowDec]] for a product of 3 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[ShowEq4T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show3 and then use [[Show4ElemT]] or [[Persist4ElemT]] to create the type class instance for ShowT. The [[Show4ElemT]] or
 *  [[Persist4Elem]] class will delegate to Show3 for some of its methods. It is better to use Show3 to override toString method than delegating the
 *  toString override to a [[ShowEq4T]] instance. */
trait Show4ed[A1, A2, A3, A4] extends Any with Show4Plused[A1, A2, A3, A4] with PersistBase4[A1, A2, A3, A4]
{ final override def paramNames: StrArr = StrArr(name1, name2, name3, name4)
  override def elemTypeNames: StrArr = StrArr(persist1.typeStr, persist2.typeStr, persist3.typeStr, persist4.typeStr)

  override def showElemStrs(way: ShowStyle): StrArr = StrArr(persist1.showT(show1, way), persist2.showT(show2, way), persist3.showT(show3, way), persist4.showT(show4, way))

  override def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr = StrArr(persist1.showDecT(show1, way, decimalPlaces, 0), persist2.showDecT(show2, way, decimalPlaces, 0),
    persist3.showDecT(show3, way, decimalPlaces, 0), persist4.showDecT(show4, way, decimalPlaces, 0))
}

/** Show classes with 4 [[Int]] parameters. */
trait ShowInt4Ed extends Any with Show4ed[Int, Int, Int, Int]
{ final override def syntaxDepth: Int = 2
  override implicit def persist1: Persist[Int] = Showing.intPersistEv
  override implicit def persist2: Persist[Int] = Showing.intPersistEv
  override implicit def persist3: Persist[Int] = Showing.intPersistEv
  override implicit def persist4: Persist[Int] = Showing.intPersistEv
}

/** Show type class for 4 parameter case classes. */
trait Show4ing[A1, A2, A3, A4, R] extends PersistBase4[A1,A2, A3, A4] with ShowNing[R]
{


}

object Show4ing
{ /** Implementation class for the [[Show4ing]] trait. */
  class Show4ingImp[A1, A2, A3, A4, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2,
    val name3: String, val fArg3: R => A3, val name4: String, val fArg4: R => A4, val opt4: Option[A4] = None, opt3In: Option[A3] = None,
    opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit val persist1: Showing[A1], val persist2: Showing[A2], val persist3: Showing[A3],
    val persist4: Showing[A4]) extends Show4ing[A1, A2, A3, A4, R]
  { val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepthT(obj: R): Int = persist1.syntaxDepthT(fArg1(obj)).max(persist2.syntaxDepthT(fArg2(obj))).max(persist3.syntaxDepthT(fArg3(obj))).
      max(persist4.syntaxDepthT(fArg4(obj))) + 1

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = StrArr(persist1.showDecT(fArg1(obj), way, maxPlaces), persist2.showDecT(fArg2(obj), way, maxPlaces),
     persist3.showDecT(fArg3(obj), way, maxPlaces), persist4.showDecT(fArg4(obj), way, maxPlaces))
  }
}

trait ShowInt4ing[R] extends Show4ing[Int, Int, Int, Int, R]
{ override def persist1: Persist[Int] = Showing.intPersistEv
  override def persist2: Persist[Int] = Showing.intPersistEv
  override def persist3: Persist[Int] = Showing.intPersistEv
  override def persist4: Persist[Int] = Showing.intPersistEv
  override def syntaxDepthT(obj: R): Int = 2
}

object ShowInt4ing
{
  /** Factory apply method for creating quick ShowDecT instances for products of 4 Ints. */
  /*def apply[R](typeStr: String, name1: String, name2: String, name3: String, name4: String, opt4: Option[Int] = None,
    opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None):
  ShowInt4ingImp[R] = new ShowInt4ingImp[R](typeStr, name1, name2, name3, name4, opt4, opt3, opt2, opt1)
*/
  class ShowInt4ingImp[R](val typeStr: String, val name1: String, fArg1: R => Int, val name2: String, fArg2: R => Int, val name3: String,
    fArg3: R => Int, val name4: String, fArg4: R => Int, val opt4: Option[Int], opt3In: Option[Int] = None, opt2In: Option[Int] = None,
    opt1In: Option[Int] = None) extends ShowInt4ing[R]
  { val opt3: Option[Int] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = StrArr(persist1.showDecT(fArg1(obj), way, maxPlaces), persist2.showDecT(fArg2(obj), way, maxPlaces),
      persist3.showDecT(fArg3(obj), way, maxPlaces), persist4.showDecT(fArg4(obj), way, maxPlaces))
  }
}

/** Produces [[Show4ing]] instances for types that extend [[Show4ed]]. */
trait Show4eding[A1, A2, A3, A4, R <: Show4ed[A1, A2, A3, A4]] extends Show4ing[A1, A2, A3, A4, R] with ShowNeding[R]

/** Produces [[ShowInt4T]] instances for types that extend [[ShowInt4Ed]]. */
trait ShowInt4eding[R <: ShowInt4Ed] extends ShowInt4ing[R] with Show4eding[Int, Int, Int, Int, R] with ShowNing[R]

object ShowInt4eding
{ /** Factory apply method for creating quick ShowDecT instances for products of 4 Ints. */
  def apply[R <: ShowInt4Ed](typeStr: String, name1: String, name2: String, name3: String, name4: String, opt4: Option[Int] = None,
    opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None):
  ShowInt4edingImp[R] = new ShowInt4edingImp[R](typeStr, name1, name2, name3, name4, opt4, opt3, opt2, opt1)

  class ShowInt4edingImp[R <: ShowInt4Ed](val typeStr: String, val name1: String, val name2: String, val name3: String, val name4: String,
    val opt4: Option[Int], opt3In: Option[Int] = None, opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends ShowInt4eding[R]
  { val opt3: Option[Int] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}
/** UnShow class for 3 logical parameter product types. */
trait Unshow4[A1, A2, A3, A4, R] extends UnshowN[R] with PersistBase4[A1, A2, A3, A4]
{ override def persist1: Unshow[A1]
  override def persist2: Unshow[A2]
  override def persist3: Unshow[A3]
  override def persist4: Unshow[A4]

  def newT: (A1, A2, A3, A4) => R

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), persist1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), persist2.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), persist3.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    def e4: EMon[A4] = ife(len > pSeq(3), persist4.fromSettingOrExpr(name4, sortedExprs(pSeq(3))), opt4.toEMon)
    e1.map4(e2, e3, e4)(newT)
  }
}

/** Persistence class for 4 logical parameter product types. */
trait Persist4[A1, A2, A3, A4, R] extends Show4ing[A1, A2, A3, A4, R] with Unshow4[A1, A2, A3, A4, R] with PersistN[R]
{ override def persist1: Persist[A1]
  override def persist2: Persist[A2]
  override def persist3: Persist[A3]
  override def persist4: Persist[A4]
}

/** Companion object for [[Persist4]] trait contains implementation class and factory apply method. */
object Persist4
{
  def apply[A1, A2, A3, A4, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, newT: (A1, A2, A3, A4) => R, opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(
    implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4]): Persist4[A1, A2, A3, A4, R] =
    new Persist4Imp(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, newT, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4)

  class Persist4Imp[A1, A2, A3, A4, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2,
    val name3: String, val fArg3: R => A3, val name4: String, val fArg4: R => A4, val newT: (A1, A2, A3, A4) => R, val opt4: Option[A4] = None, val opt3In: Option[A3] = None, opt2In: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit val persist1: Persist[A1], val persist2: Persist[A2], val persist3: Persist[A3], val persist4: Persist[A4]) extends Persist4[A1, A2, A3, A4, R]
  { val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    val defaultNum: Int = None match
    { case _ if opt3.isEmpty => 0
      case _ if opt2.isEmpty => 1
      case _ if opt1.isEmpty => 2
      case _ => 3
    }
    override def syntaxDepthT(obj: R): Int = ???

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = ???
  }
}

trait PersistInt4[R] extends Persist4[Int, Int, Int, Int, R]
{ override def persist1: Persist[Int] = Showing.intPersistEv
  override def persist2: Persist[Int] = Showing.intPersistEv
  override def persist3: Persist[Int] = Showing.intPersistEv
  override def persist4: Persist[Int] = Showing.intPersistEv
}

/** Companion object for [[PersistInt4]] trait contains implementation class and factory apply method. */
object PersistInt4
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, name3: String, fArg3: R => Int, name4: String,
    fArg4: R => Int, newT: (Int, Int, Int, Int) => R, opt4: Option[Int] = None, opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None): PersistInt4[R] =
    new PersistInt4Imp(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, newT, opt4, opt3, opt2, opt1)

  class PersistInt4Imp[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int, val name3: String,
    val fArg3: R => Int, val name4: String, val fArg4: R => Int, val newT: (Int, Int, Int, Int) => R, val opt4: Option[Int] = None,
    opt3In: Option[Int] = None, opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends PersistInt4[R]
  { val opt3: Option[Int] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)

    val defaultNum: Int = None match
    { case _ if opt3.isEmpty => 0
      case _ if opt2.isEmpty => 1
      case _ if opt1.isEmpty => 2
      case _ => 3
    }

    override def syntaxDepthT(obj: R): Int = ???

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = ???
  }
}

/*
class PersistInt4ed[R <: ShowInt4Ed] extends PersistInt4[R] with ShowInt4Ed{

}*/
