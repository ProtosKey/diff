package diff.app.presentation.mapper

import diff.app.domain.model.Point
import diff.app.presentation.basic.Mapper
import diff.app.presentation.model.PointData

object DataMapper : Mapper<Point, PointData> {
    override fun mapTo(t: Point): PointData = PointData(t.x.toFloat(), t.y.toFloat())
    override fun mapFrom(r: PointData): Point = Point(r.x.toDouble(), r.y.toDouble())
}
