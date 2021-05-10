/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

class BooleanExtensions(val thisBool : Boolean) extends AnyVal// extends PersistSimple
{
  /** Folds the Boolean, a safer and more functional alternative to using an if else statement */
  def fold[A](ifTrue: => A, ifFalse: => A): A = if (thisBool) ifTrue else ifFalse
  /** A safer alternative to using an "if" without an else */
   @inline def ifDo(f:  => Unit): Unit = if(thisBool) f else {}
   /** A safer alternative to using an if-else statement */ 
   @inline def ifElse(ifProcedure: => Unit)(elseProcedure: => Unit): Unit = if (thisBool) ifProcedure else elseProcedure
   /** if this Boolean is true modifies the value by applying the function ifMod to it else returns the value unmodified */
   def ifMod[A](value: A)(fMod: A => A) = if (thisBool) fMod(value) else value
   /** Returns the String parameter if true, returns the empty string if false */
   def ifStr(optionalString: String): String = if (thisBool) optionalString else ""
   /** Returns the the empty string if true, returns String parameter if false */   
   def ifNotStr(optionalString: String): String = if (thisBool) "" else optionalString
   def errMap[A](fp: TextPosn, errStr: String, ifTrue: => A): EMon[A] = if (thisBool) Good[A](ifTrue) else fp.bad[A](errStr)
   def errFlatMap[A](fp: TextPosn, errStr: String, ifTrue: => EMon[A]): EMon[A] = if (thisBool) (ifTrue) else fp.bad[A](errStr)
   def toOption[A](obj: A): Option[A] = if (thisBool) Some(obj) else None
   def |!& (operand: Boolean): Boolean = (thisBool || operand) && (!(thisBool && operand))
   /** This needs to be changed to by name parameters when by name varargs are allowed. I think this is coming in 12.3 */
   def ifSeq1[A](trueElem: => A): Seq[A] = if (thisBool) Seq(trueElem) else Seq()
   def ifSeq[A](trueSeq: => Seq[A]): Seq[A] = if (thisBool) trueSeq else Seq()
}