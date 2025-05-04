/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait TriangleLen2[+VT <: PtLen2] extends PolygonLen2[VT]
{ def v0x: Length
  def v0y: Length
  def v1x: Length
  def v1y: Length
  def v2x: Length
  def v2y: Length
}

class TriangleM2(val v0xMNum: Double, val v0yMNum: Double, val v1xMNum: Double, val v1yMNum: Double, val v2xMNum: Double, val v2yMNum: Double) extends
  TriangleLen2[PtM2]
{ override type ThisT = TriangleM2
  override type SideT = LSegM2
  override def typeStr: String = "TriangleM2"
  override def v0x = Metres(v0xMNum)
  override def v0y = Metres(v0yMNum)
  override def v1x = Metres(v1xMNum)
  override def v1y = Metres(v1yMNum)
  override def v2x = Metres(v2xMNum)
  override def v2y = Metres(v2yMNum)
  override def slate(operand: VecPtLen2): TriangleM2 = new TriangleM2(v0xMNum + operand.xMetresNum, v0yMNum + operand.yMetresNum, v1xMNum + operand.xMetresNum,
    v1yMNum + operand.yMetresNum, v2xMNum + operand.xMetresNum, v2yMNum + operand.yMetresNum)

  override def slate(xOperand: Length, yOperand: Length): TriangleM2 = new TriangleM2(v0xMNum + xOperand.metresNum, v0yMNum + yOperand.metresNum,
    v1xMNum + xOperand.metresNum, v1yMNum + yOperand.metresNum, v2xMNum + xOperand.metresNum, v2yMNum + yOperand.metresNum)

  override def slateX(xOperand: Length): TriangleM2 = ???

  override def slateY(yOperand: Length): TriangleM2 = ???

  override def scale(operand: Double): TriangleM2 = ???

  override def mapGeom2(operand: Length): Triangle = ???

  override def revY: TriangleM2 = ???

  override def revYIf(cond: Boolean): TriangleM2 = ???

  override def rotate180: TriangleM2 = ???

  override def rotate180If(cond: Boolean): TriangleM2 = ???

  override def rotate180IfNot(cond: Boolean): TriangleM2 = ???

  override def arrayUnsafe: Array[Double] = ???

  override def verts: Arr[PtM2] = ???
  override def side(index: Int): LSegM2 = ???

  override def sidesForeach[U](f: LSegM2 => U): Unit = ???

  override def sides: Arr[LSegM2] = ???
  override def elemFromDbls(d1: Double, d2: Double): PtM2 = ???
  override def fromArray(array: Array[Double]): TriangleM2 = ???
}