/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** CSS declaration */
trait CssDec
{
  def prop: String
}

//case class CssBGColour(colour: Colour) extends CssDec

trait CssRule {
  def selec: String
  def props: Arr[CssDec]
}

case class CssBody(props: Arr[CssDec]) extends CssRule
{
  override def selec: String = "body"
}