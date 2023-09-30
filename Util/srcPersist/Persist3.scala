/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** A base trait for [[Show3ing]] and [[Unshow3]], declares the common properties of name1 - 3 and opt1 - 3. */
trait PersistBase3Plus[A1, A2, A3] extends Any with PersistBase2Plus[A1, A2]
{ /** 3rd parameter name. */
  def name3: String

  /** The optional default value for parameter 3. */
  def opt3: Option[A3]

  /** The declaration here allows the same field to be to cover [[Showing]][A3] [[UnShow]][A3] and [[Persist]][A3]. */
  def persist3: Showing[A3] | Unshow[A3]
}

trait PersistBase3[A1, A2, A3] extends Any with PersistBase3Plus[A1, A2, A3]
{ override def paramNames: StrArr = StrArr(name1, name2, name3)
  override def numParams: Int = 3
}

/** [[Showed]] trait for classes with 3+ Show parameters. */
trait Show3Plused[A1, A2, A3] extends Any with Show2Plused[A1, A2] with PersistBase3Plus[A1, A2, A3]
{ /** The optional default value for parameter 3. */
  override def opt3: Option[A3] = None

  /** Element 3 of this Show 3+ element product. */
  def show3: A3

  override def persist3: Showing[A3]
}

/** Trait for [[ShowDec]] for a product of 3 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[ShowEq3T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show3 and then use [[Show3ElemT]] or [[Persist3ElemT]] to create the type class instance for ShowT. The [[Show3ElemT]] or
 *  [[Persist3Elem]] class will delegate to Show3 for some of its methods. It is better to use Show3 to override toString method than delegating the
 *  toString override to a [[ShowEq3T]] instance. */
trait Show3ed[A1, A2, A3] extends Any with Show3Plused[A1, A2, A3]
{ override def numParams: Int = 3
  override def paramNames: StrArr = StrArr(name1, name2, name3)
  override def elemTypeNames: StrArr = StrArr(persist1.typeStr, persist2.typeStr, persist3.typeStr)

  override def showElemStrs(way: ShowStyle): StrArr = StrArr(persist1.showT(show1, way), persist2.showT(show2, way), persist3.showT(show3, way))

  override def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr =
    StrArr(persist1.showDecT(show1, way, decimalPlaces, 0), persist2.showDecT(show2, way, decimalPlaces, 0),
    persist3.showDecT(show3, way, decimalPlaces, 0))
}

/** Show classes with 3 [[Int]] parameters. */
trait ShowInt3Ed extends Any with Show3ed[Int, Int, Int]
{ final override def syntaxDepth: Int = 2
  final override implicit def persist1: Persist[Int] = Showing.intPersistEv
  final override implicit def persist2: Persist[Int] = Showing.intPersistEv
  final override implicit def persist3: Persist[Int] = Showing.intPersistEv
}

/** Show classes with 3 [[Double]] parameters. */
trait ShowDbl3Ed extends Any with Show3ed[Double, Double, Double]
{ final override def syntaxDepth: Int = 2
  final override implicit def persist1: Persist[Double] = Showing.doublePersistEv
  final override implicit def persist2: Persist[Double] = Showing.doublePersistEv
  final override implicit def persist3: Persist[Double] = Showing.doublePersistEv
}

/** Trait for Show for product of 3[[Int]]s. This trait is implemented directly by the type in question, unlike the corresponding [[ShowDbl3Eding]]
 *  trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred
 *  over [[Show3ing]]. */
trait ShowElemInt3 extends Any with ShowInt3Ed with Int3Elem
{ final override def int1: Int = show1
  final override def int2: Int = show2
  final override def int3: Int = show2
}

/** Trait for Show for product of 3[[Double]]s. This trait is implemented directly by the type in question, unlike the corresponding [[ShowDbl3Eding]]
 *  trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred
 *  over [[Show3ing]]. */
trait ShowElemDbl3 extends Any with ShowDbl3Ed with Dbl3Elem
{ final override def dbl1: Double = show1
  final override def dbl2: Double = show2
  final override def dbl3: Double = show2
}

/** Show type class for 3 parameter case classes. */
trait Show3ing[A1, A2, A3, R] extends  ShowNing[R]

object Show3ing
{
  def apply[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1: Showing[A1], ev2: Showing[A2], ev3: Showing[A3]):
  Show3ing[A1, A2, A3, R] = new Show3TImp[A1, A2, A3, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3,opt3, opt2In, opt1In)

  class Show3TImp[A1, A2, A3, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val name3: String,
    val fArg3: R => A3, val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val ev1: Showing[A1], val ev2: Showing[A2], val ev3: Showing[A3]) extends Show3ing[A1, A2, A3, R] //with TypeStr3Plus[A1, A2, A3]
  {
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    val defaultNum: Int = None match
    { case _ if opt3.isEmpty => 0
      case _ if opt2.isEmpty => 1
      case _ if opt1.isEmpty => 2
      case _ => 3
    }

    override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))).max(ev3.syntaxDepthT(fArg3(obj))) + 1

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr =
      StrArr(ev1.showDecT(fArg1(obj),way, maxPlaces), ev2.showDecT(fArg2(obj),way, maxPlaces), ev3.showDecT(fArg3(obj),way, maxPlaces))
  }
}

/** Produces [[Show3ing]] instances for types that extend [[Show3ed]]. */
trait Show3eding[A1, A2, A3, R <: Show3ed[A1, A2, A3]] extends Show3ing[A1, A2, A3, R] with ShowNeding[R]

/** Produces [[ShowInt3T]] instances for types that extend [[ShowInt3Ed]]. */
trait ShowInt3Eding[R <: ShowInt3Ed] extends Show3eding[Int, Int, Int, R] with ShowNing[R]

object ShowInt3Eding
{ /** Factory apply method for creating quick ShowDecT instances for products of 3 Ints. */
  def apply[R <: ShowInt3Ed](typeStr: String, name1: String, name2: String, name3: String, opt3: Option[Int] = None, opt2In: Option[Int] = None,
    opt1In: Option[Int] = None):
  ShowInt3edingImp[R] = new ShowInt3edingImp[R](typeStr, name1, name2, name3, opt3, opt2In, opt1In)

  class ShowInt3edingImp[R <: ShowInt3Ed](val typeStr: String, val name1: String, val name2: String, val name3: String, val opt3: Option[Int] = None,
    opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends ShowInt3Eding[R]
  { val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

trait ShowDbl3Eding[R <: ShowDbl3Ed] extends Show3eding[Double, Double, Double, R] with ShowNing[R]

object ShowDbl3Eding
{ /** Factory apply method for creating quick ShowDecT instances for products of 3 Doubles. */
  def apply[R <: ShowElemDbl3](typeStr: String, name1: String, name2: String, name3: String, opt2: Option[Double] = None, opt1In: Option[Double] = None):
  ShowShowDbl3TImp[R] = new ShowShowDbl3TImp[R](typeStr, name1, name2, name3, opt2, opt1In)

  class ShowShowDbl3TImp[R <: ShowDbl3Ed](val typeStr: String, val name1: String, val name2: String, val name3: String, val opt3: Option[Double] = None,
    opt2In: Option[Double] = None, opt1In: Option[Double] = None) extends ShowDbl3Eding[R]
  { val opt2: Option[Double] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** UnShow class for 3 logical parameter product types. */
trait Unshow3[A1, A2, A3, R] extends UnshowN[R] with PersistBase3[A1, A2, A3]
{ override def persist1: Unshow[A1]
  override def persist2: Unshow[A2]
  override def persist3: Unshow[A3]

  /** Method fpr creating a value of type R from values A1, A2, A3. */
  def newT: (A1, A2, A3) => R

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), persist1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), persist2.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), persist3.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    e1.map3(e2, e3)(newT)
  }
}

object Unshow3
{
  def apply[A1, A2, A3, R](typeStr: String, name1: String, name2: String, name3: String, newT: (A1, A2, A3) => R,
    opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Unshow[A1], ev2: Unshow[A2], ev3: Unshow[A3]) = new
    Unshow3Imp[A1, A2, A3, R](typeStr, name1, name2, name3, newT, opt3, opt2, opt1)

  class Unshow3Imp[A1, A2, A3, R](val typeStr: String, val name1: String, val name2: String, val name3: String, val newT: (A1, A2, A3) => R,
    val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val persist1: Unshow[A1], val persist2: Unshow[A2], val persist3: Unshow[A3]) extends Unshow3[A1, A2, A3, R]
  {
    override def opt2: Option[A2] = ife(opt3.nonEmpty , opt2In, None)
    override def opt1: Option[A1] = ife(opt2.nonEmpty , opt1In, None)
  }
}

/** Persistence class for 3 logical parameter product types. */
trait Persist3[A1, A2, A3, R] extends Show3ing[A1, A2, A3, R] with Unshow3[A1, A2, A3, R] with PersistN[R]
{ override def persist1: Persist[A1]
  override def persist2: Persist[A2]
  override def persist3: Persist[A3]
}

/** Companion object for [[Persist3]] trait contains implementation class and factory apply method. */
object Persist3
{
  def apply[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(
    implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]): Persist3[A1, A2, A3, R] =
    new Persist3Imp(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT, opt3, opt2, opt1)(ev1, ev2, ev3)

  class Persist3Imp[A1, A2, A3, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2,
    val name3: String, val fArg3: R => A3, val newT: (A1, A2, A3) => R, val opt3: Option[A3] = None, opt2In: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit val persist1: Persist[A1], val persist2: Persist[A2], val persist3: Persist[A3]) extends Persist3[A1, A2, A3, R]
  { val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    val defaultNum: Int = None match{
      case _ if opt3.isEmpty => 0
      case _ if opt2.isEmpty => 1
      case _ if opt1.isEmpty => 2
      case _ => 3
    }

    override def syntaxDepthT(obj: R): Int = ???

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = ???
  }
}

/** Trait for [[Persist3]] where all three elements are [[Int]]s */
trait PersistInt3[R] extends Persist3[Int, Int, Int, R]
{ override def persist1: Persist[Int] = Showing.intPersistEv
  override def persist2: Persist[Int] = Showing.intPersistEv
  override def persist3: Persist[Int] = Showing.intPersistEv
}

/** Companion object for [[PersistInt3]] trait contains implementation class and factory apply method. */
object PersistInt3
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, name3: String, fArg3: R => Int,
    newT: (Int, Int, Int) => R, opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None): PersistInt3[R] =
    new PersistInt3Imp(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT, opt3, opt2, opt1)

  class PersistInt3Imp[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int, val name3: String,
    val fArg3: R => Int, val newT: (Int, Int, Int) => R, val opt3: Option[Int] = None, opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends
    PersistInt3[R]
  { val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
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

/** [[Persist]] trait for types that extend [[Show3ed]]. */
trait Persist3ed[A1, A2, A3, R <: Show3ed[A1, A2, A3]] extends Persist3[A1, A2, A3, R]  with PersistShowN[R] with Show3eding[A1, A2, A3, R]

/** Companion object for the [[Persist3ed]] class the persists object that extend [[ShowDec3]]. Contains an apply factory method. */
object Persist3ed
{ /** Factory apply method for [[Persist3ed]], that Persists [[ShowDec3]] objects. */
  def apply[A1, A2, A3, R <: Show3ed[A1, A2, A3]](typeStr: String, name1: String, name2: String, name3: String, newT: (A1, A2, A3) => R,
    opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1In: Persist[A1], ev2In: Persist[A2], ev3In: Persist[A3]):
    Persist3ed[A1, A2, A3, R] = new PersistShow3Imp[A1, A2, A3, R](typeStr, name1, name2, name3, newT, opt3,opt2, opt1)

  class PersistShow3Imp[A1, A2, A3, R <: Show3ed[A1, A2, A3]](val typeStr: String, val name1: String, val name2: String, val name3: String,
    val newT: (A1, A2, A3) => R, val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val persist1: Persist[A1], val persist2: Persist[A2], val persist3: Persist[A3]) extends Persist3ed[A1, A2, A3, R]
  { val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persistence class for types that extend [[ShowDbl3Ed]]. */
class PersistInt3ed[R <: ShowInt3Ed](val typeStr: String, val name1: String, val name2: String, val name3: String,
  val newT: (Int, Int, Int) => R, val opt3: Option[Int] = None, val opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends
  Persist3[Int, Int, Int, R] with PersistShowN[R] with ShowInt3Eding[R]
{ val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  override def persist1: Persist[Int] = Showing.intPersistEv
  override def persist2: Persist[Int] = Showing.intPersistEv
  override def persist3: Persist[Int] = Showing.intPersistEv
}

/** Persistence class for types that extend [[ShowDbl3Ed]]. */
class PersistDbl3ed[R <: ShowDbl3Ed](val typeStr: String, val name1: String, val name2: String, val name3: String,
  val newT: (Double, Double, Double) => R, val opt3: Option[Double] = None, val opt2In: Option[Double] = None, opt1In: Option[Double] = None) extends
  Persist3[Double, Double, Double, R] with PersistShowN[R] with ShowDbl3Eding[R]
{ val opt2: Option[Double] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  override def persist1: Persist[Double] = Showing.doublePersistEv
  override def persist2: Persist[Double] = Showing.doublePersistEv
  override def persist3: Persist[Double] = Showing.doublePersistEv
}