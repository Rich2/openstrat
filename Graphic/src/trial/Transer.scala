/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package trial

class TransBase[T](value: T, ev: Trans[T]) {  
  def trans(f: Int => Int): T = ev.trans(value, f)
  def vAdd(op: Int): T = trans(_ + op)
}

trait Transer { def fTrans(f: Int => Int): Transer }

object Transer
{
  implicit def TransFromTranserImplicit: Trans[Transer] = new Trans[Transer] {
    override def trans(obj: Transer, f: Int => Int): Transer = obj.fTrans(f)
  }
}

trait Transer12 extends Transer

case class Transer1(val myD: Double = 2.2) extends Transer12 {  
  override def fTrans(f: Int => Int): Transer1 = new Transer1(myD)
}

case class Transer2(i: Int = 5) extends Transer12 {  
  override def fTrans(f: Int => Int): Transer2 = copy()
}

case class Transer3(st: String = "Hi") extends Transer {
  override def fTrans(f: Int => Int): Transer3 = copy()
}