/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

abstract class PersistSingletons[A <: PersistSingleton](typeStr: String) extends PersistSimple[A](typeStr)
{ def singletonList: List[A]
  @inline override def show(obj: A): String = obj.str
  def fromExpr(expr: ParseExpr): EMon[A] = expr match
  { case AlphaToken(_, sym) => singletonList.find(el => Symbol(el.str) == sym).toEMon1(expr, typeStr -- "not parsed from this Expression")
    case e => bad1(e, typeStr -- "not parsed from this Expression")
  }
}

/** all the leafs of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of
  * the object, but sometimes, it may be a lengthened or shortened version of the singleton object name. */
trait PersistSingleton
{ /** The symbol for the leaf object. This will normally be different from the typeSym. */  
  def str: String
  override def toString: String = str
}
