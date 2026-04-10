/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** Creates an [[Html]] Input element that can update textContent fields on the page. */
class InputUpdaterNum(val idStr: String, val value: Double, val otherAttribs: RArr[XAtt])(using page: HtmlPageUpdater) extends HtmlInput
{
  page.inpAcc +%= this
  var depends: RArr[Callback1Num] = RArr()
  def clientCount: Int = depends.length

  override def typeAtt: TypeAtt = TypeNumberAtt
  override def valueStr: String = value.str

  def next1Id(f: Double => String): IdAtt =
  { val newtargetId: String = idStr + clientCount.str
    depends +%= Callback1Num(newtargetId, f)
    IdAtt(newtargetId)
  }
}

object InputUpdaterNum
{ /** Factory apply method to create an [[Html]] Input element that can update textContent fields on the page. */
  def apply(idStr: String, value: Double, otherAttribs: XAtt*)(using page: HtmlPageUpdater): InputUpdaterNum = new InputUpdaterNum(idStr, value, otherAttribs.toRArr)
}

/** A call back for an [[InputUpdaterNum]] that takes a simple Double => String function. */
case class Callback1Num(targetId: String, f: Double => String) extends CallbackInput