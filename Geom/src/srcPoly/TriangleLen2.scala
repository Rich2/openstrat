/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait TriangleLen2[+VT <: PtLen2] extends PolygonLen2P3[VT]
{ override def slate(operand: VecPtLen2): TriangleLen2[VT]
  override def slate(xOperand: Length, yOperand: Length): TriangleLen2[VT]
  override def slateX(xOperand: Length): TriangleLen2[VT]
  override def slateY(yOperand: Length): TriangleLen2[VT]
  override def scale(operand: Double): TriangleLen2[VT]
  def mapGeom2(operand: Length): Triangle
}

class TriangleM2(val v0xMNum: Double, val v0yMNum: Double, val v1xMNum: Double, val v1yMNum: Double, val v2xMNum: Double, val v2yMNum: Double) extends
  TriangleLen2[PtM2], PolygonM2P3
{ override type ThisT = TriangleM2
  override type SideT = LSegM2
  override def typeStr: String = "TriangleM2"

  override def slate(operand: VecPtLen2): TriangleM2 = new TriangleM2(v0xMNum + operand.xMetresNum, v0yMNum + operand.yMetresNum, v1xMNum + operand.xMetresNum,
    v1yMNum + operand.yMetresNum, v2xMNum + operand.xMetresNum, v2yMNum + operand.yMetresNum)

  override def slate(xOperand: Length, yOperand: Length): TriangleM2 = new TriangleM2(v0xMNum + xOperand.metresNum, v0yMNum + yOperand.metresNum,
    v1xMNum + xOperand.metresNum, v1yMNum + yOperand.metresNum, v2xMNum + xOperand.metresNum, v2yMNum + yOperand.metresNum)

  override def slateX(xOperand: Length): TriangleM2 = new TriangleM2(v0xMNum + xOperand.metresNum, v0yMNum, v1xMNum + xOperand.metresNum, v1yMNum,
    v2xMNum + xOperand.metresNum, v2yMNum)

  override def slateY(yOperand: Length): TriangleM2 = new TriangleM2(v0xMNum, v0yMNum + yOperand.metresNum, v1xMNum, v1yMNum + yOperand.metresNum, v2xMNum,
    v2yMNum + yOperand.metresNum)

  override def scale(operand: Double): TriangleM2 = new TriangleM2(v0xMNum * operand, v0yMNum * operand, v1xMNum * operand, v1yMNum * operand,
    v2xMNum * operand, v2yMNum * operand)

  override def revY: TriangleM2 = ???

  override def revYIf(cond: Boolean): TriangleM2 = ???

  override def rotate180: TriangleM2 = ???

  override def rotate180If(cond: Boolean): TriangleM2 = ???

  override def rotate180IfNot(cond: Boolean): TriangleM2 = ???

  override def mapGeom2(operand: Length): Triangle = Triangle(v0xMNum / operand.metresNum, v0yMNum / operand.metresNum, v1xMNum / operand.metresNum,
    v1yMNum / operand.metresNum, v2xMNum / operand.metresNum, v2yMNum / operand.metresNum)

  override def arrayUnsafe: Array[Double] = ???

  override def verts: Arr[PtM2] = ???
  override def side(index: Int): LSegM2 = ???

  override def sidesForeach[U](f: LSegM2 => U): Unit = ???

  override def sides: Arr[LSegM2] = ???
  override def elemFromDbls(d1: Double, d2: Double): PtM2 = ???
  override def fromArray(array: Array[Double]): TriangleM2 = ???
}

trait TriangleLen2Graphic extends PolygonLen2Graphic
{ override def shape: TriangleLen2[PtLen2]
  override def slate(operand: VecPtLen2): TriangleLen2Graphic
  override def slate(xOperand: Length, yOperand: Length): TriangleLen2Graphic
  override def slateX(xOperand: Length): TriangleLen2Graphic
  override def slateY(yOperand: Length): TriangleLen2Graphic
  override def scale(operand: Double): TriangleLen2Graphic
  override def mapGeom2(operand: Length): TriangleGraphic
}

case class TriangleLen2Fill(shape: TriangleLen2[PtLen2], fillFacet: FillFacet) extends TriangleLen2Graphic, PolygonLen2Fill
{ override def slate(operand: VecPtLen2): TriangleLen2Fill = TriangleLen2Fill(shape.slate(operand), fillFacet)
  override def slate(xOperand: Length, yOperand: Length): TriangleLen2Fill = TriangleLen2Fill(shape.slate(xOperand, yOperand), fillFacet)
  override def slateX(xOperand: Length): TriangleLen2Fill = TriangleLen2Fill(shape.slateX(xOperand), fillFacet)
  override def slateY(yOperand: Length): TriangleLen2Fill = TriangleLen2Fill(shape.slateY(yOperand), fillFacet)
  override def scale(operand: Double): TriangleLen2Fill = TriangleLen2Fill(shape.scale(operand), fillFacet)
  override def mapGeom2(operand: Length): TriangleFill = TriangleFill(shape.mapGeom2(operand), fillFacet)
}