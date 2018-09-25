/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** all the leafs of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of
  * the object, but sometimes, it mmay be a lengthened or shortened version of the singleton object name */
trait PersisterSingleton extends Persister
{ def str: String
  override def persist: String = str
}

trait PersisterSingletontons[A <: PersisterSingleton] extends PersistSimple[A]
{   
   def PersisterSingletontons: List[A]
   def fromExpr(expr: Expr): EMon[A] = expr match
   {
      case AlphaToken(_, str) =>
         PersisterSingletontons.find(_.str == str).toEMon1(expr, typeStr -- "not parsed from this Expression")
      case e => bad1(e, typeStr -- "not parsed from this Expression")
   }   
}