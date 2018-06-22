/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

abstract class PersistCase[R](typeSym: Symbol) extends PersistCompound[R](typeSym)
{
   def persistMems: List[Persist[_]]
   final override def syntaxDepth: Int = persistMems.map(_.syntaxDepth).max + 1
}

/** 2 Methods not implemented */
class Persist1[A1, R](typeSym: Symbol, val fParam: R => A1, val newT: A1 => R)(implicit ev1: Persist[A1]) extends
   PersistCase[R](typeSym)
{
   def persistMems = List(ev1) 
  
   override def memStrs(obj: R): List[String] = List(ev1.persist(fParam(obj)))   
   override def fromClauses(clauses: Seq[Clause]): EMon[R] = ???// fromClauses1(newT, clauses)
   override def fromParameterStatements(sts: Seq[Statement]): EMon[R] = ???// sts.errFun1(newT)(ev1)   
}

class Persist2[A1, A2, R](typeSym: Symbol, val fParam: R => (A1, A2), val newT: (A1, A2) => R)
   (implicit ev1: Persist[A1], ev2: Persist[A2])
   extends PersistCase[R](typeSym)
{  
   def persistMems = List(ev1, ev2)
   override def memStrs(obj: R): List[String] = 
   {
      val (v1, v2) = fParam(obj)
      List(ev1.persist(v1), ev2.persist(v2))
   }
   override def persist(obj: R): String =
      {
      val (p1, p2) = fParam(obj)
      typeStr - List(ev1.persistComma(p1), ev2.persistComma(p2)).strFold("; ").enParenth 
      }
   
//   override def persistSemi(obj: R): String =
//      {
//      val (p1, p2) = fParam(obj)
//      typeStr - List(ev1.persistComma(p1), ev2.persistComma(p2)).strFold(", ").enParenth 
//      }
   
   override def fromClauses(clauses: Seq[Clause]): EMon[R] = fromClauses2(newT, clauses)
   override def fromParameterStatements(sts: Seq[Statement]): EMon[R] = sts.errFun2(newT)(ev1, ev2)   
}

abstract class PersistD2[R](typeSym: Symbol, fParam: R => (Double, Double), newT: (Double, Double) => R) extends
   Persist2[Double, Double, R](typeSym, fParam, newT)

abstract class Persist3[A1, A2, A3, R](typeSym: Symbol, val fParam: R => (A1, A2, A3),
      val newT: (A1, A2, A3) => R)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]) extends
   PersistCase[R](typeSym)
{   
   def persistMems = List(ev1, ev2, ev3)
   override def memStrs(obj: R): List[String] =
   {
      val (p1, p2, p3) = fParam(obj)
      List(ev1.persist(p1), ev2.persist(p2), ev3.persist(p3))
   }
   override def fromClauses(clauses: Seq[Clause]): EMon[R] = fromClauses3(newT, clauses)
   override def fromParameterStatements(sts: Seq[Statement]): EMon[R] = sts.errFun3(newT)(ev1, ev2, ev3)
}

abstract class Persist4[A1, A2, A3, A4, R](typeSym: Symbol, val newT: (A1, A2, A3, A4) => R,
      val fParam: R => (A1, A2, A3, A4))(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], 
      ev4: Persist[A4]) extends PersistCase[R](typeSym)
{
   
   def persistMems = List(ev1, ev2, ev3, ev4)
   override def memStrs(obj: R): List[String] =
   {
      val (p1, p2, p3, p4) = fParam(obj)
      List(ev1.persist(p1), ev2.persist(p2), ev3.persist(p3), ev4.persist(p4))
   }
   override def fromClauses(clauses: Seq[Clause]): EMon[R] = fromClauses4(newT, clauses)
   override def fromParameterStatements(sts: Seq[Statement]): EMon[R] = sts.errFun4(newT)(ev1, ev2, ev3, ev4)
}

/** This trait persists an object. An object can persist this directly through inheritance or through an implicit conversion. Not the PBUild class is
 *  needed to persist the type of an object in a sequence for example. As not all persistable classes directly implement this trait an implicit conversion
 *  of type T => Persist, if its an individual object of type T must be passed as a parameter or PBuild[T] in the case of type T. */

/** A trait that persists a single Token, a short simple string */
//trait PersistStr[A] extends Any with Persist[A]
//{
//   def persistStr: String
//   final override def persist = persistStr 
//   final override def persistComma = Seq(persistStr)
//   final override def persistFull = Seq(persistStr)
//      
//}
//
//object Persist
//{
//   val maxLen = 120
//   implicit class PersistIntMethodClass(i: Int)
//   {
//      def isShort: Boolean = i <= maxLen
//   }   
//}
