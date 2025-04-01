/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgiza
import geom.*, Colour.*

trait Pyramid
{
  def sideLen: Length
  def square: SqlignLen2[PtLen2] = ???
  def squareFill = square.fill(Wheat)// SqlignLen2(sideLen).slate(offset).fill(Wheat)
  def offset: VecLen2
  def offSquare = squareFill
  val axisOffsetNum = 300
  def offVec = VecM2(axisOffsetNum, axisOffsetNum)
}

object GreatPyramid extends Pyramid
{
  override def square: SqlignLen2[PtLen2] = SqlignM2.rb(sideLen, KhafrePyramid.square.rb.slate(offVec))
  override val sideLen = 230.3.metres
  def height = 146.6.metres

  override def offset: VecLen2 = VecM2(axisOffsetNum, axisOffsetNum)
}

object KhafrePyramid extends Pyramid
{
  override def square: SqlignM2 = SqlignM2(sideLen)
  override val sideLen = 215.25.metres
  override def offset: VecLen2 = VecM2(0, 0)
}

object MenkaurePyramid extends Pyramid
{
  override def square: SqlignLen2[PtLen2] = SqlignM2.rb(sideLen, KhafrePyramid.square.rb.slate(-offVec))
    //SqlignLen2.rb(sideLen, 0.metres, 0.metres).slate(-offVec)
  override def sideLen: Length = 102.2.metres
  override def offset: VecLen2 = VecM2(-axisOffsetNum, -axisOffsetNum)
}

object Giza
{
  def pyramids = RArr(GreatPyramid, KhafrePyramid, MenkaurePyramid)
}