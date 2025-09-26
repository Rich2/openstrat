/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** An HTML input element. */
trait HtmlInput extends HtmlVoid
{ def typeAtt: TypeAtt
  override def tag: String = "input"
}

trait InputUpdater extends HtmlInput
{
  var parentCount: Int = 0
  
  /** The [[String]] of the id attribute. */
  def idStr: String

  def idAtt: IdAtt = IdAtt(idStr)

  def valueAtt: ValueAtt

  def valueStr: String

  def otherAttribs: RArr[XAtt]

  override def attribs: RArr[XAtt] = RArr(IdAtt(idStr), typeAtt, valueAtt) ++ otherAttribs  
}

class InputUpdaterNum(val idStr: String, val value: Double, val otherAttribs: RArr[XAtt])(using page: HtmlPageInput) extends InputUpdater
{
  page.inpAcc +%= this
  var depends: RArr[Callback1Num] = RArr()
  def dependsLen: Int = depends.length
  
  override def typeAtt: TypeAtt = TypeNumberAtt
  override def valueStr: String = value.str
  override def valueAtt: ValueAtt = ValueAtt(value.str)

  def next1Id(f: Double => String): IdAtt =
  {
    val newtargetId: String = idStr + parentCount.str
    parentCount += 1
    depends +%= Callback1Num(newtargetId, f)
    IdAtt(newtargetId)
  }
}

object InputUpdaterNum
{
  def apply(idStr: String, value: Double, otherAttribs: XAtt*)(using page: HtmlPageInput): InputUpdaterNum = new InputUpdaterNum(idStr, value, otherAttribs.toRArr)
}

/** A text callback from an input to a textContent. */
trait CallbackInput
{ /** The id attribute on the target HTML element whose textContent is to be updated. */
  def targetId: String
}

case class Callback1Num(targetId: String, f: Double => String) extends CallbackInput

/** Html Input element with submit type */
case class SubmitInput(valueStr: String) extends HtmlInput
{ override def typeAtt: TypeAtt = TypeSubmitAtt
  override def attribs: RArr[XAtt] = RArr(typeAtt, ValueAtt(valueStr))
}