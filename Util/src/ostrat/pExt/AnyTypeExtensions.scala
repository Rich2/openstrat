package ostrat
package pExt

/** Extension methods for Any type A. */
class AnyTypeExtensions[A](thisA: A)
{
  def fRepeat(num: Int)(f: A => A): A =
  { var acc: A = thisA
    num.doTimes(() => acc = f(acc))
    acc
  }

  def *(operand: Int): Multiple[A] = Multiple(thisA, operand)

  def nextFromList(list: List[A]): A =
  { val i: Int = list.indexOf[A](thisA)
    ife(i >= list.length - 1, list(0), list(i + 1))
  }

  def match2[B](f1: A => Boolean, v1: => B, v2: => B): B = if (f1(thisA)) v1 else v2
  def match3[B](f1: A => Boolean, v1: => B, f2: A => Boolean, v2: => B, v3: => B): B =
    if (f1(thisA)) v1 else if (f2(thisA)) v2 else v3

  def match3Excep[B](f1: A => Boolean, v1: => B, f2: A => Boolean, v2: => B, f3: A => Boolean, v3: => B, excepStr: => String): B =
    if (f1(thisA)) v1 else if (f2(thisA)) v2 else if (f3(thisA)) v3 else throw new Exception(excepStr)
}
