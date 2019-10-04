/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package trial

trait Transer { def fTrans(f: Int => Int): Transer }

object Transer
{
  implicit def TransFromTranserImplicit[T <: Transer]: Trans[T] = new Trans[T] {
    override def trans(obj: T, f: Int => Int): T = obj.fTrans(f).asInstanceOf[T]
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