/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, reflect.ClassTag, annotation.unchecked.uncheckedVariance

/** All the leaves of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of the object,
 *  but sometimes, it may be a lengthened or shortened version of the singleton object name. */
trait TellSimple extends Tell
{ /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  final override def tell(style: ShowStyle, decimalPlaces: Int = -1, minPlaces: Int = -1): String = style match
  { case ShowTyped => typeStr.appendParenth(str)
    case ShowUnderScore => "_"
    case _ => str
  }

  override def tellDepth: Int = 1
}

/** Shows a simple object like a Singleton object or a Double. For your own objects that you control it is better to use Show and its helper sub
 * rather than the sub traits of ShowT to implement your Show functionality.S */
trait ShowSimple[-A] extends Show[A]
{
  final override def syntaxDepth(obj: A): Int = 1

  override def show(obj: A, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = way match
  { case ShowTyped => typeStr + strT(obj).enParenth
    case ShowUnderScore => "_"
    case _ => strT(obj)
  }
}

object ShowSimple
{
  def apply[A](typeStrIn: String, f: A => String): ShowSimple[A] = new ShowSimple[A]
  { override def typeStr: String = typeStrIn
    override def strT(obj: A): String = f(obj)
  }
}

/** [[Show]] class for types that extend [[TellSimple]]. */
case class ShowTellSimple[R <: TellSimple](typeStr: String) extends ShowTell[R]

trait UnshowSingletons[+A <: TellSimple] extends Unshow[A]
{ /** The sequence of objects that can be unshown. */
  def singletons: RArr[A]

  def shortKeys: ArrPairStr[A @uncheckedVariance]

  override def fromExpr(expr: Expr): ExcMon[A] = expr match
  { case IdentifierToken(str) => singletons.find(el => el.str == str).orElse(shortKeys.a1FindA2(str)).toErrBi
    case expr => expr.failExc(typeStr -- "not parsed from this Expression")
  }

  def ++[AA >: A <: TellSimple](operand: UnshowSingletons[AA])(implicit ct: ClassTag[AA]): UnshowSingletons[AA] =
    UnshowSingletons(typeStr, singletons ++ operand.singletons)

  def append[AA >: A <: TellSimple](extras: AA*)(implicit ct: ClassTag[AA]): UnshowSingletons[AA] =
  { val newArr: RArr[AA] = singletons.++(extras)
    UnshowSingletons.apply[AA](typeStr, newArr)
  }
}

object UnshowSingletons
{ 
  /** Factory apply method for [[UnshowSingletons]], with no short names. For convenience, there is name overload where the singletons are passed as a repeat parameter. */
  def apply[A <: TellSimple](typeStr: String, singletons: RArr[A])(implicit ct: ClassTag[A]): UnshowSingletons[A] =
    new UnshowSingletonsImp[A](typeStr, ArrPairStr[A](), singletons)

  /** Factory apply method for [[UnshowSingletons]], with no short names. There is a name overload where the singletons are passed as an [[RArr]]. */
  def apply[A <: TellSimple](typeStr: String, singletons: A*)(implicit ct: ClassTag[A]): UnshowSingletons[A] =
    new UnshowSingletonsImp[A](typeStr, ArrPairStr[A](), singletons.toArr)

  /** Factory method for [[UnshowSingletons]], with short names. */
  def shorts[A <: TellSimple](typeStr: String, shortKeys: ArrPairStr[A], singletons: A*)(implicit ct: ClassTag[A]): UnshowSingletons[A] =
    new UnshowSingletonsImp[A](typeStr, shortKeys, singletons.toArr)

  /** General implementation class for the [[UnshowSingletons]] trait. */
  class UnshowSingletonsImp[+A <: TellSimple](val typeStr: String, val shortKeys: ArrPairStr[A @uncheckedVariance], val singletons: RArr[A]) extends
    UnshowSingletons[A]
}

class PersistBooleanNamed(typeStr: String, trueStr: String, falseStr: String) extends PersistBothSimple[Boolean](typeStr)
{ override def strT(obj: Boolean): String = ife(obj, typeStr, falseStr)

  override def fromExpr(expr: Expr): ExcMon[Boolean] = expr match
  { case IdentifierToken(str) if str == "true" || str == trueStr => Succ(true)
    case IdentifierToken(str) if str == "false" || str == falseStr => Succ(false)
    case _ => expr.exprParseErr[Boolean]
  }
}

class PersistBothTellSimple[R <: TellSimple](typeStr: String, val singletons: RArr[R])(implicit ct: ClassTag[R]) extends ShowTellSimple[R](typeStr) with
  PersistBoth[R] with UnshowSingletons[R]
{ override def shortKeys: ArrPairStr[R] = ArrPairStr[R]()
}

object PersistBothTellSimple
{
  def apply[R <: TellSimple](typeStr: String, singletons: RArr[R])(implicit ct: ClassTag[R]): PersistBothTellSimple[R] =
    new PersistBothTellSimple[R](typeStr, singletons)

  def apply[R <: TellSimple](typeStr: String, singletons: R*)(implicit ct: ClassTag[R]): PersistBothTellSimple[R] =
    new PersistBothTellSimple[R](typeStr, singletons.toArr)
}