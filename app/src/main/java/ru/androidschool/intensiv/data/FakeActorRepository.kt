package ru.androidschool.intensiv.data

import ru.androidschool.intensiv.R

class FakeActorRepository {

    fun getAllPicturesForActor(int: Int): Int {
        return when (int) {
            0 -> R.drawable.iv_mamoa
            1 -> R.drawable.iv_amber_heard
            2 -> R.drawable.iv_patrik_wilson
            3 -> R.drawable.iv_nicole_kidman
            else -> { throw Exception("it`s not ok") }
        }
    }

    fun getActors(): List<FakeActor> {
        val actorList = mutableListOf<FakeActor>()
        for (x in 0..3) {
            val fakeActor = FakeActor(
                title = "actor $x",
                resId = getAllPicturesForActor(x)
            )
            actorList.add(fakeActor)
        }

        return actorList
    }
}
