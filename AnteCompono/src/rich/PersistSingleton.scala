/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

trait PersistSingle//[R] extends Persister[R]
{
   def str: String   
   override def toString: String = str
}

trait PersistSingletons[A <: PersistSingle] extends PersistSimple[A]
{   
   def persistSingletons: List[A]
   def fromExpr(expr: Expr): EMon[A] = expr match
   {
      case AlphaToken(_, str) =>
         persistSingletons.find(_.str == str).toEMon1(expr, typeStr -- "not parsed from this Expression")
      case e => bad1(e, typeStr -- "not parsed from this Expression")
   }   
}