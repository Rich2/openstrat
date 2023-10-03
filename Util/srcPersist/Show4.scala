/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** A base trait for [[Show4]] and [[Unshow4]], declares the common properties of name1 - 4 and opt1 - 4. */
trait PersistBase4Plus[A1, A2, A3, A4] extends Any with PersistBase3Plus[A1, A2, A3]
{ /** 4th parameter name. */
  def name4: String

  /** The optional default value for parameter 4. */
  def opt4: Option[A4]

  /** The declaration here allows the same field to be to cover [[Show]][A4] [[UnShow]][A4] and [[Persist]][A4]. */
  def persist4: Show[A4] | Unshow[A4]
}

trait PersistBase4[A1, A2, A3, A4] extends Any with PersistBase4Plus[A1, A2, A3, A4]
{ override def paramNames: StrArr = StrArr(name1, name2, name3, name4)
  override def numParams: Int = 4
}

/** Show type class for 4 parameter case classes. */
trait Show4[A1, A2, A3, A4, R] extends PersistBase4[A1,A2, A3, A4] with ShowN[R]
{ override def persist1: Show[A1]
  override def persist2: Show[A2]
  override def persist3: Show[A3]
  override def persist4: Show[A4]
  def fArg1: R => A1
  def fArg2: R => A2
  def fArg3: R => A3
  def fArg4: R => A4

  override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = StrArr(persist1.showDecT(fArg1(obj), way, maxPlaces), persist2.showDecT(fArg2(obj), way, maxPlaces),
    persist3.showDecT(fArg3(obj), way, maxPlaces), persist4.showDecT(fArg4(obj), way, maxPlaces))
}

object Show4
{ /** Implementation class for the general cases of [[Show4]] trait. */
  class Show4Imp[A1, A2, A3, A4, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2,
    val name3: String, val fArg3: R => A3, val name4: String, val fArg4: R => A4, val opt4: Option[A4] = None, opt3In: Option[A3] = None,
    opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit val persist1: Show[A1], val persist2: Show[A2], val persist3: Show[A3],
    val persist4: Show[A4]) extends Show4[A1, A2, A3, A4, R]
  { val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepthT(obj: R): Int = persist1.syntaxDepthT(fArg1(obj)).max(persist2.syntaxDepthT(fArg2(obj))).max(persist3.syntaxDepthT(fArg3(obj))).
      max(persist4.syntaxDepthT(fArg4(obj))) + 1
  }
}

/** Produces [[Show]] type class instances for 4 [[Int]] types. */
trait ShowInt4[R] extends Show4[Int, Int, Int, Int, R]
{ override def persist1: Persist[Int] = Show.intPersistEv
  override def persist2: Persist[Int] = Show.intPersistEv
  override def persist3: Persist[Int] = Show.intPersistEv
  override def persist4: Persist[Int] = Show.intPersistEv
  override def syntaxDepthT(obj: R): Int = 2
}

object ShowInt4
{
  /** Factory apply method for creating quick ShowDecT instances for products of 4 Ints. */
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, name3: String, fArg3: R => Int, name4: String,
    fArg4: R => Int, opt4: Option[Int] = None, opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None):
  ShowInt4Imp[R] = new ShowInt4Imp[R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, opt4, opt3, opt2, opt1)

  class ShowInt4Imp[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int, val name3: String,
    val fArg3: R => Int, val name4: String, val fArg4: R => Int, val opt4: Option[Int], opt3In: Option[Int] = None, opt2In: Option[Int] = None,
    opt1In: Option[Int] = None) extends ShowInt4[R]
  { val opt3: Option[Int] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = StrArr(persist1.showDecT(fArg1(obj), way, maxPlaces), persist2.showDecT(fArg2(obj), way, maxPlaces),
      persist3.showDecT(fArg3(obj), way, maxPlaces), persist4.showDecT(fArg4(obj), way, maxPlaces))
  }
}

/** UnShow class for 4 logical parameter product types. */
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
trait Persist4[A1, A2, A3, A4, R] extends Show4[A1, A2, A3, A4, R] with Unshow4[A1, A2, A3, A4, R] with PersistN[R]
{ override def persist1: Persist[A1]
  override def persist2: Persist[A2]
  override def persist3: Persist[A3]
  override def persist4: Persist[A4]
}

/** Companion object for [[Persist4]] trait contains implementation class and factory apply method. */
object Persist4
{
  def apply[A1, A2, A3, A4, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, newT: (A1, A2, A3, A4) => R, opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None,
    opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4]): Persist4[A1, A2, A3, A4, R] =
    new Persist4Imp(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, newT, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4)

  class Persist4Imp[A1, A2, A3, A4, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2,
    val name3: String, val fArg3: R => A3, val name4: String, val fArg4: R => A4, val newT: (A1, A2, A3, A4) => R, val opt4: Option[A4] = None,
    val opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit val persist1: Persist[A1],
    val persist2: Persist[A2], val persist3: Persist[A3], val persist4: Persist[A4]) extends Persist4[A1, A2, A3, A4, R]
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

trait PersistInt4[R] extends Persist4[Int, Int, Int, Int, R] with ShowInt4[R]
{ override def persist1: Persist[Int] = Show.intPersistEv
  override def persist2: Persist[Int] = Show.intPersistEv
  override def persist3: Persist[Int] = Show.intPersistEv
  override def persist4: Persist[Int] = Show.intPersistEv
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
  }
}