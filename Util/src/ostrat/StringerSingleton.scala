/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** all the leafs of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of
  * the object, but sometimes, it may be a lengthened or shortened version of the singleton object name. */
trait SingletonLeaf //extends Stringer
{ /** The symbol for the leaf object. This will normally be different from the typeSym. */
  def objSym: Symbol
  def str: String = objSym.name
  override def toString: String = objSym.name
}

abstract class PersistSingletons[A <: SingletonLeaf](typeStr: String) extends PersistSimple[A](typeStr)
{ def singletonList: List[A]
  @inline override def show(obj: A): String = obj.str
  def fromExpr(expr: ParseExpr): EMon[A] = expr match
  { case AlphaToken(_, sym) => singletonList.find(_.objSym == sym).toEMon1(expr, typeStr -- "not parsed from this Expression")
    case e => bad1(e, typeStr -- "not parsed from this Expression")
  }
}