/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, reflect.ClassTag, annotation.unchecked.uncheckedVariance

/** All the leaves of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of the object,
 *  but sometimes, it may be a lengthened or shortened version of the singleton object name. */
trait TellSimple extends TellQuanta
{ /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  final override def tell(style: ShowStyle): String = style match
  { case ShowTyped => typeStr.appendParenth(str)
    case ShowUnderScore => "_"
    case _ => str
  }

  override def syntaxDepth: Int = 1
}

/** Shows a simple object like a Singleton object or a Double. For your own objects that you control it is better to use Show and its helper sub
 * rather than the sub traits of ShowT to implement your Show functionality.S */
trait ShowSimple[-A] extends Show[A]
{
  final override def syntaxDepthT(obj: A): Int = 1

  override def showDec(obj: A, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = way match
  { case ShowTyped => typeStr + strT(obj).enParenth
    case ShowUnderScore => "_"
    case _ => strT(obj)
  }
}

/** [[Show]] class for types that extend [[TellSimple]]. */
case class ShowTellSimple[R <: TellSimple](typeStr: String) extends ShowTell[R]

trait UnshowSingletons[+A <: TellSimple] extends Unshow[A]
{ /** The sequence of objects that can be unshown. */
  def singletons: RArr[A]

  def fromExpr(expr: Expr): EMon[A] = expr match
  { case IdentifierToken(str) => singletons.find(el => el.str == str).toEMon1(expr, typeStr -- "not parsed from this Expression")
    case e => bad1(e, typeStr -- "not parsed from this Expression")
  }

  def ++[AA >: A <: TellSimple](operand: UnshowSingletons[AA])(implicit ct: ClassTag[AA]): UnshowSingletons[AA] =
    UnshowSingletons(typeStr, singletons ++ operand.singletons)

  def append[AA >: A <: TellSimple](extras: AA*)(implicit ct: ClassTag[AA]): UnshowSingletons[AA] =
  { val newArr: RArr[AA] = singletons.appendIter(extras)
    UnshowSingletons.apply[AA](typeStr, newArr)
  }
}

object UnshowSingletons
{ def apply[A <: TellSimple](typeStr: String, singletons: RArr[A]): UnshowSingletons[A] = new UnshowSingletonsImp[A](typeStr, singletons)

  def apply[A <: TellSimple](typeStr: String, singletons: A*)(implicit ct: ClassTag[A]): UnshowSingletons[A] =
    new UnshowSingletonsImp[A](typeStr, singletons.toArr)

  class UnshowSingletonsImp[+A <: TellSimple](val typeStr: String, val singletons: RArr[A]) extends UnshowSingletons[A]
}

/** I think this class may be redundant and can be replace by a more general PersistSum class for displaying algebraic sum types. */
abstract class PersistSingletons[A <: TellSimple](typeStr: String) extends PersistSimple[A](typeStr) with UnshowSingletons[A]
{
  @inline override def strT(obj: A): String = obj.str
}

object PersistSingletons
{
  def apply[A <: TellSimple](typeStr: String, singletonsIn: RArr[A]): PersistSingletons[A] = new PersistSingletons[A](typeStr)
  { override def singletons: RArr[A] = singletonsIn
  }

  def apply[A <: TellSimple](typeStr: String, singletonsIn: A*)(implicit ct: ClassTag[A]): PersistSingletons[A] = new PersistSingletons[A](typeStr)
  {  override def singletons: RArr[A] = singletonsIn.toArr
  }
}