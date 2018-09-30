/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** all the leafs of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of
  * the object, but sometimes, it mmay be a lengthened or shortened version of the singleton object name. */
trait StringerSingleton extends Stringer
{ def sym: Symbol
  override def str: String = sym.name
}

abstract class PersistSingletons[A <: StringerSingleton](typeSym: Symbol) extends PersistSimple[A](typeSym)
{ def singletonList: List[A]
  @inline override def persist(obj: A): String = obj.str
  def fromExpr(expr: Expr): EMon[A] = expr match
  { case AlphaToken(_, str) => singletonList.find(_.str == str).toEMon1(expr, typeStr -- "not parsed from this Expression")
    case e => bad1(e, typeStr -- "not parsed from this Expression")
  }
}