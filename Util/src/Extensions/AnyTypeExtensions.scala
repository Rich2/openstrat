/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Extension methods for Any type A. */
class AnyTypeExtensions[A](thisA: A)
{
  def fRepeat(num: Int)(f: A => A): A =
  { var acc: A = thisA
    num.doTimes(() => acc = f(acc))
    acc
  }

  /** Extension method on any type creates Multiple class of that type. */
  def *(operand: Int): Multiple[A] = Multiple(thisA, operand)

  def nextFromArr(arr: Sequ[A]): A =
  { val i: Int = arr.indexOf(thisA)
    ife(i >= arr.length - 1, arr(0), arr(i + 1))
  }

  /** Short hand alternative to a 3 case match statement, throwing an exception id no match. */
  def match3Excep[B](f1: A => Boolean, v1: => B, f2: A => Boolean, v2: => B, f3: A => Boolean, v3: => B, excepStr: => String): B =
    if (f1(thisA)) v1 else if (f2(thisA)) v2 else if (f3(thisA)) v3 else throw new Exception(excepStr)
}

/** Extension methods for Any type A. */
class AnyRefTypeExtensions[A <: AnyRef](thisA: A)
{ /** If the condition is true apply the function to this value of type A and return a modified type A else return thisA unmodified. */
  def ifMod(b: Boolean, fTrue: A => A): A = if (b) fTrue(thisA) else thisA

  /** If the condition is true thisA unmodified else apply the function to this value of type A and return a modified value of type A. */
  def ifNotMod(b: Boolean, fTrue: A => A): A = if (b) thisA else fTrue(thisA)

  /** Short hand alternative to a 2 case match statement. */
  def match2[B](f1: A => Boolean, v1: => B, v2: => B): B = if (f1(thisA)) v1 else v2

  /** Short hand alternative to a 3 case match statement. */
  def match3[B](f1: A => Boolean, v1: => B, f2: A => Boolean, v2: => B, v3: => B): B =
    if (f1(thisA)) v1 else if (f2(thisA)) v2 else v3
}