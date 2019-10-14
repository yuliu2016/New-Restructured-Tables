package ca.warp7.android.scouting.v5.entry

data class DataPoint(val type: Int, val value: Int, val time: Int) : Iterable<Byte> {
    override fun iterator() = byteArrayOf(type.toByte(), value.toByte(), time.toByte()).iterator()
}