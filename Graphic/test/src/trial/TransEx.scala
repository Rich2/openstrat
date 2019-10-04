/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package trial

object TransEx
{
  val a1: Transer1 = Transer1()
  val a2 = Transer2()
  val r1 = a1.vAdd(3)
  val l1a: List[Transer1] = List(a1, a1).vAdd(8)
  val l1 = List(a1, a2).vAdd(2)
  val oa: Option[Transer1] = Some(a1)
  val ob: Option[Transer2] = Some(a2)
  val ro1 = oa.vAdd(14)
  val ro2: List[Option[Transer12]] = List(oa, ob, None).vAdd(4)
  val o3: Option[List[Transer3]] = Some(List(Transer3("Hello!"), Transer3(), Transer3("GoodBye"))).vAdd(-52)
}