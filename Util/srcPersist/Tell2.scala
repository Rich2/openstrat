/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for [[TellDec]] for a product of 2 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[Show2]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show2 and then use [[ShowTell2]] or [[Persist2ElemT]] to create the type class instance for ShowT. The [[ShowTell2]] or
 *  [[PersistTell2]] class will delegate to Show2 for some of its methods. It is better to use Show2 to override toString method than delegating the
 *  toString override to a [[Show2]] instance. */
trait Tell2[A1, A2] extends Any with Show2Plused[A1, A2] with PersistBase2[A1, A2]
{ override def paramNames: StrArr = StrArr(name1, name2)
  def elemTypeNames: StrArr = StrArr(persist1.typeStr, persist2.typeStr)
  def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr = StrArr(persist1.showDecT(show1, way, decimalPlaces, 0), persist2.showDecT(show2, way, decimalPlaces, 0))

  def el1Show(style: ShowStyle = ShowStandard, maxPlaces: Int = -1): String = persist1.showDecT(show1, style, maxPlaces, maxPlaces): String
  def el2Show(style: ShowStyle = ShowStandard, maxPlaces: Int = -1): String = persist2.showDecT(show2, style, maxPlaces, maxPlaces): String

  override def str: String = typeStr + (persist1.strT(show1).appendSemicolons(persist2.strT(show2))).enParenth

  override def syntaxDepth: Int = persist1.syntaxDepthT(show1).max(persist2.syntaxDepthT(show2)) + 1
}

/** Trait for Show for product of 2 Ints. This trait is implemented directly by the type in question, unlike the corresponding [[ShowTellInt2]] trait
 *  which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 *  [[Show2]]. */
trait TellInt2 extends Any with Tell2[Int, Int]
{ final override implicit def persist1: Show[Int] = Show.intPersistEv
  final override implicit def persist2: Show[Int] = Show.intPersistEv
  final override def syntaxDepth: Int = 2
}

/** Shows a class with 2 [[Double]] components. Note if the class also extends ElemDbl2, the dbl1 and dbl2 properties, may be different to the show1
 * and show2 properties, unless the class extends [[TellElemDbl2]]. */
trait TellDbl2 extends Any with Tell2[Double, Double]
{ final override implicit def persist1: Show[Double] = Show.doublePersistEv
  final override implicit def persist2: Show[Double] = Show.doublePersistEv
  final override def syntaxDepth: Int = 2
}

/** Trait for Show for product of 2 Doubles that is also an [[Dbl2Elem]]. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowTellDbl2]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2]]. */
trait TellElemDbl2 extends Any with TellDbl2 with Dbl2Elem
{ final override def dbl1: Double = show1
  final override def dbl2: Double = show2
}

/** Trait for Show for product of 2 Ints that is also an ElemInt2. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowTellInt2]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2]]. */
trait TellElemInt2 extends Any with TellInt2 with Int2Elem
{ final override def int1: Int = show1
  final override def int2: Int = show2
}

/** Type class trait for Showing [[Tell2]] objects. */
trait ShowTell2[A1, A2, R <: Tell2[A1, A2]] extends Show2[A1, A2, R] with ShowTell[R]
{ override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = obj.showElemStrDecs(way, maxPlaces)
  override def fArg1: R => A1 = _.show1
  override def fArg2: R => A2 = _.show2
}

object ShowTell2
{
  def apply[A1, A2, R<: Tell2[A1, A2]](typeStr: String)(
    implicit ev1: Show[A1], ev2: Show[A2]): ShowTell2[A1, A2, R] =
    new Show2edingImp[A1, A2, R](typeStr)

  /** Implementation class for the general cases of the [[ShowTell2]] trait. */
  class Show2edingImp[A1, A2, R<: Tell2[A1, A2]](val typeStr: String)(implicit val persist1: Show[A1], val persist2: Show[A2]) extends
    ShowTell2[A1, A2, R]
}

/** A trait for making quick ShowT instances for [[TellDbl2]] types. It uses the functionality of the [[TellDbl2]]. */
trait ShowTellDbl2[R <: TellDbl2] extends ShowTell2[Double, Double, R]
{ override implicit def persist1: Persist[Double] = Show.doublePersistEv
  override implicit def persist2: Persist[Double] = Show.doublePersistEv
}

object ShowTellDbl2
{ /** Factory apply method for creating quick ShowT instances for products of 2 Doubles. */
  def apply[R <: TellElemDbl2](typeStr: String): ShowTellDbl2Imp[R] = new ShowTellDbl2Imp[R](typeStr)

  /** Implementation class for the general cases of the [[ShowTellDbl2]] trait. */
  class ShowTellDbl2Imp[R <: TellDbl2](val typeStr: String) extends ShowTellDbl2[R]
}

/** A trait for making quick ShowT instances for [[TellElemInt2]] classes. It uses the functionality of the [[ShowelemInt2]]. */
trait ShowTellInt2[R <: TellInt2] extends ShowInt2[R] with ShowTell2[Int, Int, R]

object ShowTellInt2
{ /** Factory apply method for creating quick ShowT instances for products of 2 [[Int]]s. */
  def apply[R <: TellElemInt2](typeStr: String, name1: String, name2: String, opt2: Option[Int] = None, opt1In: Option[Int] = None):
  ShowTellInt2[R] = new ShowShowInt2TImp[R](typeStr, name1, name2, opt2,opt1In)

  class ShowShowInt2TImp[R <: TellElemInt2](val typeStr: String, val name1: String, val name2: String, val opt2: Option[Int] = None,
    opt1In: Option[Int] = None) extends ShowTellInt2[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persist type class for types that extends [[Tell2]]. */
trait PersistTell2[A1, A2, R <: Tell2[A1, A2]] extends Persist2[A1, A2, R] with ShowTell2[A1, A2, R]

/** Companion object for the [[PersistTell2]] class the persists object that extend [[Tell2]]. Contains an apply factory method. */
object PersistTell2
{ /** Factory apply method for [[PersistTell2]], that Persists [[Tell2]] objects. */
  def apply[A1, A2, R <: Tell2[A1, A2]](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1In: Persist[A1], ev2In: Persist[A2]): PersistTell2[A1, A2, R] =
    new PersistShow2Imp[A1, A2, R](typeStr, name1, name2, newT, opt2, opt1)

  class PersistShow2Imp[A1, A2, R <: Tell2[A1, A2]](val typeStr: String, val name1: String, val name2: String, val newT: (A1, A2) => R,
    val opt2: Option[A2] = None, opt1In: Option[A1] = None)(implicit val persist1: Persist[A1], val persist2: Persist[A2]) extends PersistTell2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persistence type class for types that extend [[TellInt2]]. */
class PersistTellInt2[R <: TellInt2](val typeStr: String, val name1: String, val name2: String, val newT: (Int, Int) => R,
  val opt2: Option[Int] = None, opt1In: Option[Int] = None) extends PersistInt2[R] with PersistTell2[Int, Int, R] with ShowTellInt2[R]
{ val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
}

object PersistTellInt2
{ /** Factory apply method for [[PersistTellInt2]] that persists objects of type [[TellElemInt2]]. */
  def apply[R <: TellElemInt2](typeStr: String, name1: String, name2: String, newT: (Int, Int) => R): PersistTellInt2[R] =
    new PersistTellInt2[R](typeStr, name1, name2, newT)
}

/** Persistence class for types that extend [[TellDbl2]]. */
class PersistTellDbl2[R <: TellDbl2](val typeStr: String, val name1: String, val name2: String, val newT: (Double, Double) => R,
  val opt2: Option[Double] = None, opt1In: Option[Double] = None) extends PersistTell2[Double, Double, R] with ShowTellDbl2[R]
{ val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
}