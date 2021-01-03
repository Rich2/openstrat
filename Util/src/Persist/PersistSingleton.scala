/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** I think this class may be redundant and can be replace by a more general PersistSum class for displaying algebraic sum types. */
abstract class PersistSingletons[A <: PersistSingleton](typeStr: String) extends PersistSimple[A](typeStr)
{ def singletonList: List[A]
  @inline override def strT(obj: A): String = obj.str
  def fromExpr(expr: ParseExpr): EMon[A] = expr match
  { case IdentifierLowerOnlyToken(_, str) => singletonList.find(el => el.str == str).toEMon1(expr, typeStr -- "not parsed from this Expression")
    case e => bad1(e, typeStr -- "not parsed from this Expression")
  }
}

/** all the leafs of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of
  * the object, but sometimes, it may be a lengthened or shortened version of the singleton object name. */
trait PersistSingleton extends Show
{ /** The string for the leaf object. This will normally be different from the typeStr in the instance of the PersistSingletons. */
  def str: String
  override def toString: String = str
}