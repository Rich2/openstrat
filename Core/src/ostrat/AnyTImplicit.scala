/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Implicit def from A <: Any to AnyImplicit[A] in the package object */
class AnyTImplicit[A](val thisA: A) extends AnyVal
{
   def -+[B >: A](operand: B): ::[B] = ::(thisA, operand :: Nil)
   def -+[B >: A](operand: List[B]): ::[B] = ::(thisA, operand)
   def -+[B >: A, That](that: scala.collection.GenTraversableOnce[B])(implicit bf: scala.collection.generic.CanBuildFrom[Seq[A], B, That]):
      That = ::(thisA, Nil) ++ that 
   def ifMod(b: => Boolean, fMod: A => A): A = if (b) fMod(thisA) else thisA
   def ifMod(ifFunc: A => Boolean, fMod: A => A): A = if (ifFunc(thisA)) fMod(thisA) else thisA
   def ifNotMod(b: Boolean, fMod: A => A): A = if (b) thisA else fMod(thisA)
   def ifNotMod(ifNotFunc: A => Boolean, fMod: A => A): A = if (ifNotFunc(thisA))thisA else fMod(thisA)
   //def commasToString(others: Any*): String = others.foldLeft(thisA.toString)(_ - ", " - _.toString)
   def *(operand: Int): Multiple[A] = Multiple(thisA, operand)
   
}