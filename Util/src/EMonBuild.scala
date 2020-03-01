package ostrat

trait EMonBuild[T]
{
  type EMonT <: EMon[T]
  type GoodT <: Good[T] with EMonT
  def apply(value: T): GoodT
}
