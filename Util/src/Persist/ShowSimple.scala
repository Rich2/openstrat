/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** All the leaves of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of the object,
 *  but sometimes, it may be a lengthened or shortened version of the singleton object name. */
trait ShowSimple extends Show
{ /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowDecT]] type class instances. */
  final override def show(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match
  { case ShowTyped => typeStr.appendParenth(str)
    case ShowUnderScore => "_"
    case _ => str
  }

  override def syntaxDepth: Int = 1
}

/** Shows a simple object like a Singleton object or a Double. For your own objects that you control it is better to use Show and its helper sub
 * rather than the sub traits of ShowT to implement your Show functionality.S */
trait ShowSimpleT[-A] extends ShowDecT[A]
{
  final override def syntaxDepthT(obj: A): Int = 1

  override def showDecT(obj: A, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = way match
  { case ShowTyped => typeStr + strT(obj).enParenth
    case ShowUnderScore => "_"
    case _ => strT(obj)
  }
}

/** A Persist class described by a single value. This may be removed. Its not clear whether this means a single token or not. */
abstract class PersistSimple[A](val typeStr: String) extends ShowSimpleT[A] with Persist[A]

/** I think this class may be redundant and can be replace by a more general PersistSum class for displaying algebraic sum types. */
abstract class PersistSingletons[A <: ShowSimple](typeStr: String) extends PersistSimple[A](typeStr)
{ def singletonList: List[A]
  @inline override def strT(obj: A): String = obj.str
  def fromExpr(expr: Expr): EMon[A] = expr match
  { case IdentLowerToken(_, str) => singletonList.find(el => el.str == str).toEMon1(expr, typeStr -- "not parsed from this Expression")
    case e => bad1(e, typeStr -- "not parsed from this Expression")
  }
}