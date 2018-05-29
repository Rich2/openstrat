///* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
//package geom
//
//object ArrowGraphic
//{
//   def apply(isRight: Boolean, size: Double = 1, ratio: Double = 0.25,  width: Double = 0.5): Seq[Line2Seg] =
//      if (isRight)
//         left(size, ratio, width)
//      else right(size, ratio, width)
//   def right(size: Double = 1, ratio: Double = 0.25,  width: Double = 0.5): Seq[Line2Seg] = 
//   {
//		def lineBase = Vec2(- size /2, 0)
//		def lineTip = Vec2(size/ 2, 0)
//		def up = Vec2(size * (0.5 - ratio),size * width / 2)
//		def down = Vec2(size * (0.5 - ratio), - size * width / 2)
//		Line2Seg](lineBase -> lineTip, up -> lineTip, down -> lineTip) 
//   }
//   def left(size: Double = 1, ratio: Double = 0.25,  width: Double = 0.5): Seq[Line2Seg] =
//   {
//		def lineBase = Vec2(size /2, 0)
//		def lineTip = Vec2(-size/ 2, 0)
//		def up = Vec2(-size * (0.5 - ratio), size * width / 2)
//		def down = Vec2(-size * (0.5 - ratio), - size * width / 2)
//		SeqLine2Seg(lineBase -> lineTip, up -> lineTip, down -> lineTip) 
//	}
//}