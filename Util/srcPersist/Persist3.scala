/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, reflect.ClassTag

/** A base trait for Tell3+, Show3+ and Unshow3+ classes. Declares the common properties of name1 - 3 and opt1 - 3. */
trait Persist3Plus[A1, A2, A3] extends Any with Persist2Plus[A1, A2]
{ /** 3rd parameter name. */
  def name3: String

  /** The optional default value for parameter 3. */
  def opt3: Option[A3]
}

/** Common base trait for [[Show3]], [[Unshow3]] and [[Persist3]]. */
trait Persist3[A1, A2, A3] extends Any with Persist3Plus[A1, A2, A3]
{ final override def paramNames: StrArr = StrArr(name1, name2, name3)
  final override def numParams: Int = 3
}

/** [[Show]] type class for 3 field product types. */
trait Show3Plus[A1, A2, A3, A] extends Show2PlusFixed[A1, A2, A] with Persist3Plus[A1, A2, A3]
{ /** Gets the 2nd show field from the object. The Show fields do not necessarily correspond to the fields in memory.*/
  def fArg3: A => A3

  /** Show type class instance for the 2nd Show field. */
  implicit def show3Ev: Show[A3]

  /** Shows parameter 3 of the object. */
  def show3(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = show3Ev.show(fArg3(obj), way, maxPlaces, minPlaces)
}

/** Show type class for 3 parameter case classes. */
trait Show3[A1, A2, A3, A] extends Show3Plus[A1, A2, A3, A] with Persist3[A1, A2, A3] with ShowNFixed[A]
{ override def fieldShows: RArr[Show[_]] = RArr(show1Ev, show2Ev, show3Ev)

  override def strs(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr = opt3 match
  { case Some(a3) if opt1 == Some(fArg1(obj)) && opt2 == Some(fArg2(obj)) && a3 == fArg3(obj) => StrArr()
    case Some(a3) if opt2 == Some(fArg2(obj)) && a3 == fArg3(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces))
    case Some(a3) if a3 == fArg3(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces))
    case _ => StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces), show3(obj, way, maxPlaces, minPlaces))
  }

  override def syntaxDepth(obj: A): Int = show1Ev.syntaxDepth(fArg1(obj)).max(show2Ev.syntaxDepth(fArg2(obj))).max(show3Ev.syntaxDepth(fArg3(obj))) + 1
}

object Show3
{ /** Factory apply method for creating [[Show]] type class instances / evidence for objects with 3 components. */
  def apply[A1, A2, A3, A](typeStr: String, name1: String, fArg1: A => A1, name2: String, fArg2: A => A2, name3: String, fArg3: A => A3,
    opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit show1Ev: Show[A1], show2Ev: Show[A2], show3Ev: Show[A3],
    ct: ClassTag[A]):
  Show3[A1, A2, A3, A] = new Show3Imp[A1, A2, A3, A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, ArrPairStr[A](), opt3, opt2In, opt1In)

  /** Factory apply method for creating [[Show]] type class instances / evidence for objects with 3 components, with added short hand names for
   * certain values. */
  def shorts[A1, A2, A3, A](typeStr: String, name1: String, fArg1: A => A1, name2: String, fArg2: A => A2, name3: String, fArg3: A => A3,
    shortKeys: ArrPairStr[A], opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit show1Ev: Show[A1],
    show2Ev: Show[A2], show3Ev: Show[A3]): Show3[A1, A2, A3, A] =
    new Show3Imp[A1, A2, A3, A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, shortKeys, opt3, opt2In, opt1In)

  /** Implementation class for the general cases of the [[Show3]] trait. */
  class Show3Imp[A1, A2, A3, A](val typeStr: String, val name1: String, val fArg1: A => A1, val name2: String, val fArg2: A => A2, val name3: String,
    val fArg3: A => A3, val shortKeys: ArrPairStr[A], override val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val show1Ev: Show[A1], val show2Ev: Show[A2], val show3Ev: Show[A3]) extends Show3[A1, A2, A3, A]
  { override val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** [[Show]] type class trait for types with 3 [[Int]] Show components. */
trait ShowInt3[A] extends Show3[Int, Int, Int, A]
{ def show1Ev: Show[Int] = Show.intEv
  def show2Ev: Show[Int] = Show.intEv
  def show3Ev: Show[Int] = Show.intEv
  override def syntaxDepth(obj: A): Int = 2
}

object ShowInt3
{ /** Factory apply method for creating [[Show]] type class instances / evidence for objects with 3 [[Int]] components. */
  def apply[A](typeStr: String, name1: String, fArg1: A => Int, name2: String, fArg2: A => Int, name3: String, fArg3: A => Int,
    newT: (Int, Int, Int) => A, opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None)(implicit ct: ClassTag[A]): ShowInt3[A] =
    new ShowInt3Imp[A](typeStr, name1, fArg1, name2, fArg2, name3: String, fArg3, newT, ArrPairStr[A](), opt3, opt2, opt1)

  /** Implementation class for [[Show]] type class instances / evidence for objects with 3 [[Int]] components. */
  class ShowInt3Imp[A](val typeStr: String, val name1: String, val fArg1: A => Int, val name2: String, val fArg2: A => Int, val name3: String,
    val fArg3: A => Int, val newT: (Int, Int, Int) => A, val shortKeys: ArrPairStr[A], override val opt3: Option[Int], opt2In: Option[Int] = None,
    opt1In: Option[Int] = None) extends ShowInt3[A]
  { override val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** [[Show]] type class trait for types with 3 [[Double]] Show components. */
trait ShowDbl3[A] extends Show3[Double, Double, Double, A]
{ def show1Ev: Show[Double] = Show.doubleEv
  def show2Ev: Show[Double] = Show.doubleEv
  def show3Ev: Show[Double] = Show.doubleEv
  override def syntaxDepth(obj: A): Int = 2
}

object ShowDbl3
{ /** Factory apply method for creating [[Show]] type class instances / evidence for objects with 3 [[Double]] components. */
  def apply[A](typeStr: String, name1: String, fArg1: A => Double, name2: String, fArg2: A => Double, name3: String, fArg3: A => Double,
    opt3: Option[Double] = None, opt2: Option[Double] = None, opt1: Option[Double] = None)(implicit classTag: ClassTag[A]): ShowDbl3[A] =
    new ShowDbl3Imp[A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, ArrPairStr[A](), opt3, opt2, opt1)

  /** Implementation class for [[Show]] type class instances / evidence for objects with 3 [[Double]] components. */
  class ShowDbl3Imp[A](val typeStr: String, val name1: String, val fArg1: A => Double, val name2: String, val fArg2: A => Double, val name3: String,
    val fArg3: A => Double, val shortKeys: ArrPairStr[A], override val opt3: Option[Double] = None, opt2In: Option[Double] = None,
    opt1In: Option[Double] = None) extends ShowDbl3[A]
  { override def opt2: Option[Double] = ife(opt3.nonEmpty, opt2In, None)
    override def opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** common trait for [[Unshow]] type class instances for sum types with 3 or more components. */
trait Unshow3Plus[A1, A2, A3, A] extends Unshow2Plus[A1, A2, A] with Persist3Plus[A1, A2, A3]
{ /** The [[Unshow]] type class instance for type A3. */
  def unshow3Ev: Unshow[A3]
}

/** [[Unshow]] class for 3 logical parameter product types. */
trait Unshow3[A1, A2, A3, A] extends Unshow3Plus[A1, A2, A3, A] with Persist3[A1, A2, A3]
{ /** Method fpr creating a value of type R from values A1, A2, A3. */
  def newT: (A1, A2, A3) => A

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[A] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), unshow1Ev.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), unshow2Ev.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), unshow3Ev.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    e1.map3(e2, e3)(newT)
  }
}

object Unshow3
{ /** Factory apply method for creating [[Unshow]] type class instances / evidence for objects with 3 components. */
  def apply[A1, A2, A3, A](typeStr: String, name1: String, name2: String, name3: String, newT: (A1, A2, A3) => A, opt3: Option[A3] = None,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit persist1: Unshow[A1], persist2: Unshow[A2], persist3: Unshow[A3], ct: ClassTag[A]):
  Unshow3[A1, A2, A3, A] = new Unshow3Imp[A1, A2, A3, A](typeStr, name1, name2, name3, ArrPairStr[A](), newT, opt3, opt2, opt1)

  /** Factory apply method for creating [[Unshow]] type class instances / evidence for objects with 3 components, with added short hand names for
   * certain values. */
  def shorts[A1, A2, A3, A](typeStr: String, name1: String, name2: String, name3: String, shortKeys: ArrPairStr[A], newT: (A1, A2, A3) => A,
    opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit persist1: Unshow[A1], persist2: Unshow[A2],
    persist3: Unshow[A3]): Unshow3[A1, A2, A3, A] = new Unshow3Imp[A1, A2, A3, A](typeStr, name1, name2, name3, shortKeys, newT, opt3, opt2, opt1)

  class Unshow3Imp[A1, A2, A3, A](val typeStr: String, val name1: String, val name2: String, val name3: String, val shortKeys: ArrPairStr[A],
    val newT: (A1, A2, A3) => A, override val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit
    val unshow1Ev: Unshow[A1], val unshow2Ev: Unshow[A2], val unshow3Ev: Unshow[A3]) extends Unshow3[A1, A2, A3, A]
  {
    override def opt2: Option[A2] = ife(opt3.nonEmpty , opt2In, None)
    override def opt1: Option[A1] = ife(opt2.nonEmpty , opt1In, None)
  }
}

/** [[Unshow]] type class instances with 3 [[Int]] components. */
class UnshowInt3[A](val typeStr: String, val name1: String, val name2: String, val name3: String, val newT: (Int, Int, Int) => A,
  val shortKeys: ArrPairStr[A], override val opt3: Option[Int] = None, opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends Unshow3[Int, Int, Int, A]
{
  override val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
  override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  override def unshow1Ev: Unshow[Int] = Unshow.intEv
  override def unshow2Ev: Unshow[Int] = Unshow.intEv
  override def unshow3Ev: Unshow[Int] = Unshow.intEv

  val defaultNum: Int = None match
  { case _ if opt3.isEmpty => 0
    case _ if opt2.isEmpty => 1
    case _ if opt1.isEmpty => 2
    case _ => 3
  }
}

/** Companion object for [[UnshowInt3]] trait contains factory apply method. */
object UnshowInt3
{ def apply[A](typeStr: String, name1: String, name2: String, name3: String, newT: (Int, Int, Int) => A, opt3: Option[Int] = None,
    opt2: Option[Int] = None, opt1: Option[Int] = None)(implicit classTag: ClassTag[A]): UnshowInt3[A] =
    new UnshowInt3(typeStr, name1, name2, name3, newT, ArrPairStr[A](), opt3, opt2, opt1)
}

/** [[Unshow]] type class instances with 3 [[Double]] components. */
trait UnshowDbl3[A] extends Unshow3[Double, Double, Double, A]
{ override def unshow1Ev: Unshow[Double] = Unshow.doubleEv
  override def unshow2Ev: Unshow[Double] = Unshow.doubleEv
  override def unshow3Ev: Unshow[Double] = Unshow.doubleEv
}

object UnshowDbl3
{ /** Factory apply method for [[Unshow]] type class instances with 3 [[Double]] components. */
  def apply[A](typeStr: String, name1: String, name2: String, name3: String, newT: (Double, Double, Double) => A, opt3: Option[Double] = None,
    opt2: Option[Double] = None, opt1: Option[Double] = None)(implicit ct: ClassTag[A]): UnshowDbl3[A] =
    new UnshowDbl3Imp[A](typeStr, name1, name2, name3, newT, ArrPairStr[A](), opt3, opt2, opt1)

  /** [[Unshow]] type class instances with 3 [[Double]] components. */
  class UnshowDbl3Imp[A](val typeStr: String, val name1: String, val name2: String, val name3: String, val newT: (Double, Double, Double) => A,
    val shortKeys: ArrPairStr[A], override val opt3: Option[Double] = None, opt2In: Option[Double] = None, opt1In: Option[Double] = None) extends
    UnshowDbl3[A]
  { override val opt2: Option[Double] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Class to provide both [[Show]] and [[Unshow]] type class instances for objects with 2 [[Double]] components. */
class Persist3Both[A1, A2, A3, A](val typeStr: String, val name1: String, val fArg1: A => A1, val name2: String, val fArg2: A => A2,
  val name3: String, val fArg3: A => A3, val shortKeys: ArrPairStr[A], val newT: (A1, A2, A3) => A, override val opt3: Option[A3], opt2In: Option[A2],
  opt1In: Option[A1])(implicit val show1Ev: Show[A1], val show2Ev: Show[A2], val show3Ev: Show[A3], val unshow1Ev: Unshow[A1], val unshow2Ev: Unshow[A2],
  val unshow3Ev: Unshow[A3]) extends PersistBoth[A] with Show3[A1, A2, A3, A] with Unshow3[A1, A2, A3, A]
{ override val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

}

object Persist3Both
{ /** Factory apply method for creating both [[Show3]] and [[Unshow3]] type type class instances / evidence. */
  def apply[A1, A2, A3, A](typeStr: String, name1: String, fArg1: A => A1, name2: String, fArg2: A => A2, name3: String, fArg3: A => A3,
    newT: (A1, A2, A3) => A, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit show1Ev: Show[A1],
    show2Ev: Show[A2], show3Ev: Show[A3], unshow1Ev: Unshow[A1], unshow2Ev: Unshow[A2], unshow3Ev: Unshow[A3], classTag: ClassTag[A]):
    Persist3Both[A1, A2, A3, A] = new Persist3Both[A1, A2, A3, A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, ArrPairStr[A](), newT,opt3, opt2,
    opt1)

  /** Factory method for creating both [[Show3]] and [[Unshow3]] type type class instances / evidence with short hand labels. */
  def shorts[A1, A2, A3, A](typeStr: String, name1: String, fArg1: A => A1, name2: String, fArg2: A => A2, name3: String, fArg3: A => A3,
    shorts: ArrPairStr[A], newT: (A1, A2, A3) => A, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    show1Ev: Show[A1], show2Ev: Show[A2], show3Ev: Show[A3], unshow1Ev: Unshow[A1], unshow2Ev: Unshow[A2], unshow3Ev: Unshow[A3],
    classTag: ClassTag[A]):
    Persist3Both[A1, A2, A3, A] = new Persist3Both[A1, A2, A3, A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, shorts, newT, opt3,
    opt2, opt1)

  /** Factory method for creating both [[Show3]] and [[Unshow3]] type class instances / evidence, by explicitly passing the [[PersistBoth]] type class
   * instances for the two components. */
  def explicit[A1, A2, A3, A](typeStr: String, name1: String, fArg1: A => A1, name2: String, fArg2: A => A2, name3: String, fArg3: A => A3,
    newT: (A1, A2, A3) => A, persist1Ev: PersistBoth[A1], persist2Ev: PersistBoth[A2], persist3Ev: PersistBoth[A3], opt3: Option[A3] = None,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit classTag: ClassTag[A]): Persist3Both[A1, A2, A3, A] =
    new Persist3Both[A1, A2, A3, A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, ArrPairStr[A](), newT, opt3, opt2, opt1)(persist1Ev,
      persist2Ev, persist3Ev, persist1Ev, persist2Ev, persist3Ev)

  /** Factory method for creating both [[Show3]] and [[Unshow3]] type class instances / evidence, by explicitly passing the [[Show]] and [[Unshow]]
   *  type class instances for the two components. */
  def explicitFull[A1, A2, A3, A](typeStr: String, name1: String, fArg1: A => A1, name2: String, fArg2: A => A2, name3: String, fArg3: A => A3,
    newT: (A1, A2, A3) => A, show1Ev: Show[A1], show2Ev: Show[A2], show3Ev: Show[A3], unshow1Ev: Unshow[A1], unshow2Ev: Unshow[A2],
    unshow3Ev: Unshow[A3], opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ct: ClassTag[A]):
    Persist3Both[A1, A2, A3, A] =
    new Persist3Both[A1, A2, A3, A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, ArrPairStr[A](), newT, opt3, opt2, opt1)(show1Ev, show2Ev,
    show3Ev, unshow1Ev, unshow2Ev, unshow3Ev)
}

/** Class to provide both [[Show]] and [[Unshow]] type class instances with 3 [[Double]] components. */
class PersistDbl3Both[A](val typeStr: String, val name1: String, val fArg1: A => Double, val name2: String, val fArg2: A => Double, val name3: String,
  val fArg3: A => Double, val newT: (Double, Double, Double) => A, val shortKeys: ArrPairStr[A], override val opt3: Option[Double] = None,
  opt2In: Option[Double] = None, opt1In: Option[Double] = None) extends PersistBoth[A] with ShowDbl3[A] with UnshowDbl3[A]
{ override val opt2: Option[Double] = ife(opt3.nonEmpty, opt2In, None)
  override val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
}

object PersistDbl3Both
{ /** Factory apply method for creating [[Unshow2]] with 2 [[IDouble]] component type class instances. */
  def apply[A](typeStr: String, name1: String, fArg1: A => Double, name2: String, fArg2: A => Double, name3: String, fArg3: A => Double,
    newT: (Double, Double, Double) => A, opt2: Option[Double] = None, opt1In: Option[Double] = None)(implicit classTag: ClassTag[A]):
    PersistDbl3Both[A] = new PersistDbl3Both[A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT, ArrPairStr[A](), opt2, opt1In)
}