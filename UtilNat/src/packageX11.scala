/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scalanative.unsafe._

package object x11
{
  type Display = Ptr[CStruct2[CInt, CInt]]
  type Dummy = Ptr[Byte]
  type Window = Ptr[CStruct11[Display, Dummy, CInt, CInt, CUnsignedInt, CUnsignedInt, CInt, CUnsignedInt, Dummy, CUnsignedLong, Dummy]]
  type GC = Ptr[Byte]
  type Drawable = Ptr[Byte]
  val ExposureMask: Long = 32768
  val KeyPressMask: Long = 1
  type C64 = CStruct8[CLong, CLong, CLong, CLong, CLong, CLong, CLong, CLong]
  type C512 = CStruct8[C64, C64, C64, C64, C64, C64, C64, C64]
  type Event = CStruct2[CInt, C512]
  type EventPtr = Ptr[Event]
}
