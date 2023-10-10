/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** [[Tell]] trait for classes with 2+ Show parameters. */
trait Tell2Plused[A1, A2] extends Any with TellN with PersistBase2Plus[A1, A2]
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

/** Trait for [[TellDec]] for a product of 2 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[Show2]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show2 and then use [[ShowTell2]] or [[Persist2ElemT]] to create the type class instance for ShowT. The [[ShowTell2]] or
 *  [[PersistTell2]] class will delegate to Show2 for some of its methods. It is better to use Show2 to override toString method than delegating the
 *  toString override to a [[Show2]] instance. */
trait Tell2[A1, A2] extends Any with Tell2Plused[A1, A2] with PersistBase2[A1, A2]
{ override def paramNames: StrArr = StrArr(name1, name2)
  def elemTypeNames: StrArr = StrArr(show1.typeStr, show2.typeStr)
  def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr = StrArr(show1.showDecT(tell1, way, decimalPlaces, 0), show2.showDecT(tell2, way, decimalPlaces, 0))

  def el1Show(style: ShowStyle = ShowStandard, maxPlaces: Int = -1): String = show1.showDecT(tell1, style, maxPlaces, maxPlaces): String
  def el2Show(style: ShowStyle = ShowStandard, maxPlaces: Int = -1): String = show2.showDecT(tell2, style, maxPlaces, maxPlaces): String

  override def str: String = typeStr + (show1.strT(tell1).appendSemicolons(show2.strT(tell2))).enParenth

  override def syntaxDepth: Int = show1.syntaxDepthT(tell1).max(show2.syntaxDepthT(tell2)) + 1
}

/** Trait for Show for product of 2 Ints. This trait is implemented directly by the type in question, unlike the corresponding [[ShowTellInt2]] trait
 *  which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 *  [[Show2]]. */
trait TellInt2 extends Any with Tell2[Int, Int]
{ final override implicit def show1: Show[Int] = Show.intPersistEv
  final override implicit def show2: Show[Int] = Show.intPersistEv
  final override def syntaxDepth: Int = 2
}

/** Shows a class with 2 [[Double]] components. Note if the class also extends ElemDbl2, the dbl1 and dbl2 properties, may be different to the show1
 * and show2 properties, unless the class extends [[TellElemDbl2]]. */
trait TellDbl2 extends Any with Tell2[Double, Double]
{ final override implicit def show1: Show[Double] = Show.doublePersistEv
  final override implicit def show2: Show[Double] = Show.doublePersistEv
  final override def syntaxDepth: Int = 2
}

/** Trait for Show for product of 2 Doubles that is also an [[Dbl2Elem]]. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowTellDbl2]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2]]. */
trait TellElemDbl2 extends Any with TellDbl2 with Dbl2Elem
{ final override def dbl1: Double = tell1
  final override def dbl2: Double = tell2
}

/** Trait for Show for product of 2 Ints that is also an ElemInt2. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowTellInt2]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2]]. */
trait TellElemInt2 extends Any with TellInt2 with Int2Elem
{ final override def int1: Int = tell1
  final override def int2: Int = tell2
}

/** Type class trait for Showing [[Tell2]] objects. */
trait ShowTell2[A1, A2, R <: Tell2[A1, A2]] extends Show2[A1, A2, R] with ShowTell[R]
{ override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = obj.showElemStrDecs(way, maxPlaces)
  override def fArg1: R => A1 = _.tell1
  override def fArg2: R => A2 = _.tell2
}

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
{ override implicit def show1: Persist[Double] = Show.doublePersistEv
  override implicit def show2: Persist[Double] = Show.doublePersistEv
}

object ShowTellDbl2
{ /** Factory apply method for creating quick ShowT instances for products of 2 Doubles. */
  def apply[R <: TellDbl2](typeStr: String): ShowTellDbl2[R] = new ShowTellDbl2Imp[R](typeStr)

  /** Implementation class for the general cases of the [[ShowTellDbl2]] trait. */
  class ShowTellDbl2Imp[R <: TellDbl2](val typeStr: String) extends ShowTellDbl2[R]
}

trait ShowTellElemDbl2[R <: TellElemDbl2] extends ShowTellDbl2[R]
{

}

object ShowTellElemDbl2
{ /** Factory apply method for creating quick ShowT instances for products of 2 Doubles. */
  def apply[R <: TellElemDbl2](typeStr: String): ShowTellDbl2Imp[R] = new ShowTellDbl2Imp[R](typeStr)

  /** Implementation class for the general cases of the [[ShowTellDbl2]] trait. */
  class ShowTellDbl2Imp[R <: TellDbl2](val typeStr: String) extends ShowTellDbl2[R]
}

/** A trait for making quick ShowT instances for [[TellElemInt2]] classes. It uses the functionality of the [[ShowelemInt2]]. */
trait ShowTellInt2[R <: TellInt2] extends ShowInt2[R] with ShowTell2[Int, Int, R]
object ShowTellInt2
{ /** Factory apply method for creating quick ShowT instances for products of 2 [[Int]]s. */
  def apply[R <: TellInt2](typeStr: String, opt2: Option[Int] = None, opt1: Option[Int] = None): ShowTellInt2[R] =
    new ShowTellInt2TImp[R](typeStr, opt2,opt1)

  class ShowTellInt2TImp[R <: TellInt2](val typeStr: String, val opt2: Option[Int] = None, opt1In: Option[Int] = None) extends
    ShowTellInt2[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

object ShowTellElemInt2
{ /** Factory apply method for creating quick ShowT instances for products of 2 [[Int]]s. */
  def apply[R <: TellElemInt2](typeStr: String, opt2: Option[Int] = None, opt1: Option[Int] = None): ShowTellInt2[R] =
    new ShowTellElemInt2TImp[R](typeStr, opt2,opt1)

  class ShowTellElemInt2TImp[R <: TellElemInt2](val typeStr: String, val opt2: Option[Int] = None, opt1In: Option[Int] = None) extends
    ShowTellInt2[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}