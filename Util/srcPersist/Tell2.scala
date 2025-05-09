/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag

/** [[Tell]] trait for classes with 2+ Show parameters. */
trait Tell2Plused[A1, A2] extends Any, TellN, Persist2Plus[A1, A2]
{ /** The optional default value for parameter 1. */
  override def opt1: Option[A1] = None

  /** The optional default value for parameter 2. */
  override def opt2: Option[A2] = None

  /** Element 1 of this Tell2+ element product. */
  def tell1: A1

  /** Element 2 of this Tell2+ element product. */
  def tell2: A2

  def show1: Show[A1]
  def show2: Show[A2]
}

/** Trait for [[TellDec]] for a product of 2 logical elements. This trait is implemented directly by the type in question, unlike the corresponding [[Show2]]
 * trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to inherit from Show2
 * and then use [[ShowTell2]] or [[Persist2ElemT]] to create the type class instance for ShowT. The [[ShowTell2]] or [[PersistTell2]] class will delegate to
 * Show2 for some of its methods. It is better to use Show2 to override toString method than delegating the toString override to a [[Show2]] instance. */
trait Tell2[A1, A2] extends Any, Tell2Plused[A1, A2], Persist2[A1, A2]
{ override def paramNames: StrArr = StrArr(name1, name2)
  def elemTypeNames: StrArr = StrArr(show1.typeStr, show2.typeStr)
  override def tellElemStrs(way: ShowStyle, decimalPlaces: Int = -1, minPlaces: Int = 0): StrArr = StrArr(
    show1.show(tell1, way, decimalPlaces, minPlaces), show2.show(tell2, way, decimalPlaces, minPlaces))

  def el1Show(style: ShowStyle = ShowStdNoSpace, maxPlaces: Int = -1): String = show1.show(tell1, style, maxPlaces, maxPlaces): String
  def el2Show(style: ShowStyle = ShowStdNoSpace, maxPlaces: Int = -1): String = show2.show(tell2, style, maxPlaces, maxPlaces): String

  override def tellDepth: Int = show1.syntaxDepth(tell1).max(show2.syntaxDepth(tell2)) + 1
}

/** Trait for Show for product of 2 Ints. This trait is implemented directly by the type in question, unlike the corresponding [[ShowTellInt2]] trait which
 * externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over [[Show2]]. */
trait TellInt2 extends Any with Tell2[Int, Int]
{ final override implicit def show1: Show[Int] = Show.intEv
  final override implicit def show2: Show[Int] = Show.intEv
  final override def tellDepth: Int = 2
}

/** Shows a class with 2 [[Double]] components. Note if the class also extends ElemDbl2, the dbl1 and dbl2 properties, may be different to the show1 and show2
 * properties, unless the class extends [[TellElemDbl2]]. */
trait TellDbl2 extends Any with Tell2[Double, Double]
{ final override implicit def show1: Show[Double] = Show.doubleEv
  final override implicit def show2: Show[Double] = Show.doubleEv
  final override def tellDepth: Int = 2
}

/** Trait for Show for product of 2 Doubles that is also an [[Dbl2Elem]]. This trait is implemented directly by the type in question, unlike the corresponding
 * [[ShowTellDbl2]] trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is
 * preferred over [[Show2]]. */
trait TellElemDbl2 extends Any with TellDbl2 with Dbl2Elem
{ final override def dbl1: Double = tell1
  final override def dbl2: Double = tell2
}

/** Trait for Show for product of 2 Ints that is also an ElemInt2. This trait is implemented directly by the type in question, unlike the corresponding
 * [[ShowTellInt2]] trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is
 * preferred over [[Show2]]. */
trait TellElemInt2 extends Any with TellInt2 with Int2Elem
{ final override def int1: Int = tell1
  final override def int2: Int = tell2
}

/** Type class trait for Showing [[Tell2]] objects. */
trait ShowTell2[A1, A2, R <: Tell2[A1, A2]] extends ShowTell[R]

object ShowTell2
{
  def apply[A1, A2, R<: Tell2[A1, A2]](typeStr: String)(
    implicit ev1: Show[A1], ev2: Show[A2]): ShowTell2[A1, A2, R] =
    new ShowTell2Imp[A1, A2, R](typeStr)

  /** Implementation class for the general cases of the [[ShowTell2]] trait. */
  class ShowTell2Imp[A1, A2, R<: Tell2[A1, A2]](val typeStr: String)(implicit val show1: Show[A1], val show2: Show[A2]) extends
    ShowTell2[A1, A2, R]
}

/** A trait for making quick ShowT instances for [[TellDbl2]] types. It uses the functionality of the [[TellDbl2]]. */
trait ShowTellDbl2[R <: TellDbl2] extends ShowTell2[Double, Double, R]

object ShowTellDbl2
{ /** Factory apply method for creating quick ShowT instances for products of 2 Doubles. */
  def apply[R <: TellDbl2](typeStr: String): ShowTellDbl2[R] = new ShowTellDbl2Imp[R](typeStr)

  /** Implementation class for the general cases of the [[ShowTellDbl2]] trait. */
  class ShowTellDbl2Imp[R <: TellDbl2](val typeStr: String) extends ShowTellDbl2[R]
}

trait ShowTellElemDbl2[R <: TellElemDbl2] extends ShowTellDbl2[R]

object ShowTellElemDbl2
{ /** Factory apply method for creating quick ShowT instances for products of 2 Doubles. */
  def apply[R <: TellElemDbl2](typeStr: String): ShowTellDbl2Imp[R] = new ShowTellDbl2Imp[R](typeStr)

  /** Implementation class for the general cases of the [[ShowTellDbl2]] trait. */
  class ShowTellDbl2Imp[R <: TellDbl2](val typeStr: String) extends ShowTellDbl2[R]
}

/** A trait for making quick ShowT instances for [[TellElemInt2]] classes. It uses the functionality of the [[ShowelemInt2]]. */
trait ShowTellInt2[R <: TellInt2] extends ShowTell2[Int, Int, R]

object ShowTellInt2
{ /** Factory apply method for creating quick ShowT instances for products of 2 [[Int]]s. */
  def apply[R <: TellInt2](typeStr: String, opt2: Option[Int] = None, opt1: Option[Int] = None): ShowTellInt2[R] = new ShowTellInt2Imp[R](typeStr, opt2, opt1)

  /** A trait for making quick ShowT instances for [[TellElemInt2]] classes. It uses the functionality of the [[ShowelemInt2]]. */
  class ShowTellInt2Imp[R <: TellInt2](val typeStr: String, val opt2: Option[Int] = None, opt1In: Option[Int] = None) extends ShowTellInt2[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

trait Tell2Repeat[A1, A2] extends Tell
{
  /** Element 1 of this Tell2+ element product. */
  def tell1: A1

  /** Element 2 of this Tell2+ element product. */
  def tell2Foreach(f: A2 => Unit): Unit

  def tell2MapStrs(f: A2 => String): StrArr =
  { val buff = StringBuff()
    tell2Foreach(a2 => buff.grow(f(a2)))
    buff.toArr
  }

  def show1: Show[A1]

  def show2: Show[A2]

  def el1Show(style: ShowStyle = ShowStdNoSpace, maxPlaces: Int = -1, minPlaces: Int = 0): String = show1.show(tell1, style, maxPlaces, minPlaces): String

  //def el2Show(elem: A2, style: ShowStyle = ShowStandard, maxPlaces: Int = -1, minPlaces: Int = 0): String = show2.show(elem, style, maxPlaces, minPlaces): String

  def tellElemStrs(way: ShowStyle, decimalPlaces: Int = -1, minPlaces: Int = 0): StrArr = el1Show(way, decimalPlaces, minPlaces) %:
    tell2MapStrs(a2 => show2.show(a2, way, decimalPlaces, minPlaces))

  //def tellSemisNames(maxPlaces: Int = -1, minPlaces: Int = 0): String =
    //paramNames.zipMap(tellElemStrs(ShowStandard, maxPlaces))((n, s) => n + " = " + s).mkStr("; ")

  override def str: String = tell(ShowStdNoSpace)

  override def tell(style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String =
  { def semisStr = tellElemStrs(ShowCommas, maxPlaces).mkStr("; ")

    style match
    { case ShowSemis => semisStr
      case ShowCommas => tellElemStrs(ShowCommas, maxPlaces).mkStr(", ")
      //case ShowFieldNames => typeStr + tellSemisNames(maxPlaces, minPlaces).enParenth
      //case ShowSemisNames => tellSemisNames(maxPlaces, minPlaces)

      /*case ShowStdTypedFields => {
        val inner = paramNames.zipMap2(elemTypeNames, tellElemStrs(ShowStandard, maxPlaces))((n, t, s) => n + ": " + t + " = " + s).mkStr("; ")
        typeStr + inner.enParenth
      }*/

      case _ => typeStr.appendParenth(semisStr)
    }
  }

  override def tellDepth: Int = 3
}

/** Class to provide both [[Show]] and [[Unshow]] type class instances for [[Tell2]] objects. */
trait PersistTell2[A1, A2, A <: Tell2[A1, A2]] extends PersistTell[A] with
  ShowTell2[A1, A2, A] with Unshow2[A1, A2, A]


object PersistTell2
{ /** Factory apply method for creating [[PersistTell2]] type class instances / evidence. */
  def apply[A1, A2, A <: Tell2[A1, A2]](typeStr: String, name1: String, name2: String, newT: (A1, A2) => A, opt2: Option[A2] = None, opt1: Option[A1] = None)(
    using unshow1Ev: Unshow[A1], unshow2Ev: Unshow[A2], classTag: ClassTag[A]): PersistTell2[A1, A2, A] =
    new PersistTell2Imp[A1, A2, A](typeStr, name1, name2, ArrPairStr[A](), newT, opt2, opt1, unshow1Ev, unshow2Ev)

  /** Factory method for creating [[PersistTell2]] type class instances / evidence with short labels. */
  def shorts[A1, A2, A <: Tell2[A1, A2]](typeStr: String, name1: String, name2: String, shorts: ArrPairStr[A], newT: (A1, A2) => A, opt2: Option[A2] = None,
    opt1: Option[A1] = None)(using unshow1Ev: Unshow[A1], unshow2Ev: Unshow[A2]): PersistTell2[A1, A2, A] =
    new PersistTell2Imp[A1, A2, A](typeStr, name1, name2, shorts, newT, opt2, opt1, unshow1Ev, unshow2Ev)

  /** Factory method for creating [[PersistTell2]] type class instances / evidence, by explicitly passing the [[Unshow]] type class instances for the two
   * components. */
  def explicit[A1, A2, A <: Tell2[A1, A2]](typeStr: String, name1: String, name2: String, newT: (A1, A2) => A,unshow1Ev: Unshow[A1], unshow2Ev: Unshow[A2],
    opt2: Option[A2] = None,opt1: Option[A1] = None)(using ct: ClassTag[A]): PersistTell2[A1, A2, A] =
    new PersistTell2Imp[A1, A2, A](typeStr, name1, name2, ArrPairStr[A](), newT, opt2, opt1, unshow1Ev, unshow2Ev)

  /** Class to provide both [[Show]] and [[Unshow]] type class instances for [[Tell2]] objects. */
  class PersistTell2Imp[A1, A2, A <: Tell2[A1, A2]](val typeStr: String, val name1: String, val name2: String, val shortKeys: ArrPairStr[A],
    val newT: (A1, A2) => A, val opt2: Option[A2], opt1In: Option[A1], val unshow1Ev: Unshow[A1], val unshow2Ev: Unshow[A2]) extends PersistTell2[A1, A2, A]
  { override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Class to provide both [[Show]] and [[Unshow]] type class instances for [[Tell2]] objects. */
class PersistTellInt2[A <: TellInt2](val typeStr: String, val name1: String, val name2: String, val shortKeys: ArrPairStr[A], val newT: (Int, Int) => A,
  override val opt2: Option[Int], opt1In: Option[Int]) extends ShowTellInt2[A], PersistTell2[Int, Int, A], UnshowInt2[A]
{ override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
}

object PersistTellInt2
{ /** Factory apply method for creating [[PersistTellInt2]] type class instances / evidence. */
  def apply[A <: TellInt2](typeStr: String, name1: String, name2: String, newT: (Int, Int) => A, opt2: Option[Int] = None, opt1: Option[Int] = None)(using
    ctA: ClassTag[A]): PersistTellInt2[A] =
    new PersistTellInt2[A](typeStr, name1, name2, ArrPairStr[A](), newT, opt2, opt1)

  /** Factory method for creating [[PersistTellInt2]] type class instances / evidence with short labels. */
  def shorts[A <: TellInt2](typeStr: String, name1: String, name2: String, shorts: ArrPairStr[A], newT: (Int, Int) => A, opt2: Option[Int] = None,
    opt1: Option[Int] = None): PersistTellInt2[A] = new PersistTellInt2[A](typeStr, name1, name2, shorts, newT, opt2, opt1)
}