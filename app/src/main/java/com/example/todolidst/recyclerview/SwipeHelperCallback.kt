package com.example.todolidst.recyclerview

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.todolidst.R
import kotlin.math.min

class SwipeHelperCallback (private val recyclerViewAdapter : CustomAdapter)  : ItemTouchHelper.Callback(){
    // swipe_view 를 swipe 했을 때 <삭제> 화면이 보이도록 고정하기 위한 변수들
    private var currentPosition: Int? = null    // 현재 선택된 recycler view의 position
    private var previousPosition: Int? = null   // 이전에 선택했던 recycler view의 position
    private var currentDx = 0f                  // 현재 x 값
    private var clamp = 0f                      // 고정시킬 크기

    fun isSlided():Boolean{
        if(currentDx != 0f){
            return true
        }else return false
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0,LEFT or RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    // 사용자와의 상호작용과 해당 애니메이션도 끝났을 때 호출
    // drag된 view가 drop 되었거나, swipe가 cancel되거나 complete되었을 때 호출
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        previousPosition = viewHolder.adapterPosition
        getDefaultUIUtil().clearView(getView(viewHolder))
        super.clearView(recyclerView, viewHolder)
    }

    // ItemTouchHelper가 ViewHolder를 스와이프 되었거나 드래그 되었을 때 호출
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let{
            currentPosition = viewHolder.adapterPosition
            getDefaultUIUtil().onSelected(getView(it))
        }
    }

    override fun onChildDraw( //아이템을 터치하거나 스와이프하는 등 뷰에 변화가 생길 경우 호출
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ACTION_STATE_SWIPE){
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder) // 고정할지 말지 결정, true : 고정함 false : 고정 안 함
            val newX = clampViewPositionHorizontal(dX,isClamped,isCurrentlyActive) // newX 만큼 이동(고정 시 이동 위치/고정 해제 시 이동 위치 결정)

            //고정시킬 시 애니메이션 추가
            if(newX == -clamp) {
                getView(viewHolder).animate().translationX(-clamp).setDuration(100L).start()
                return
            }
            currentDx = newX
            getDefaultUIUtil().onDraw(
                c, recyclerView, view, newX,dY, actionState, isCurrentlyActive
            )
        }
    }

    // 사용자가 view를 swipe 했다고 간주할 최소 속도 정하기
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float = defaultValue * 10

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        // -clamp 이상 swipe 시 isClamped를 true로 변경 아닐시 false로 변경
        setTag(viewHolder,currentDx <= -clamp)
        return 2f
    }
    private fun getView(viewHolder: RecyclerView.ViewHolder) : View = viewHolder.itemView.findViewById(
        R.id.container
    )

    private fun clampViewPositionHorizontal( //container를 swipe 했을때 (수정,삭제) 화면이 보이도록 고정
        dx: Float,
        isClamped: Boolean,
        isCurrentlyActive: Boolean
    ) : Float{
        //RIGHT 방향으로 swipe 막기
        val max = 0f

        //고정할 수 있으면
        val newX = if(isClamped){
            //현재 swipe 중이면 swipe되는 여역 제한
            if(isCurrentlyActive)
                //오른쪽 swipe 일때
                    if(dx<0) dx/3 - clamp
                    //왼쪽 swipe 일때
                    else dx - clamp
            //seip 중이 아니면 고정
            else -clamp
        }
        //고정할 수 없으면 newX는 스와이프한 만큼
        else dx / 2

        //newX가 0보다 작은지 확인
        return min(newX,max)
    }
    //view가 swipe 되었을 때 고정될 크기 설정
    fun setClamp(clamp : Float){this.clamp = clamp}

    // 다른 View가 swipe 되거나 터치되면 고정 해제
    fun removePreviousClamp(recyclerView: RecyclerView){
        //현재 선택한 view가 이전에 선택한 view와 같으면 패스
        if(currentPosition == previousPosition) return

        //이전에 선택한 위치의 view 고정 해체
        previousPosition?.let{
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
            getView(viewHolder).animate().x(0f).setDuration(100L).start()
            setTag(viewHolder, false)
            previousPosition = null
        }
    }


    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped : Boolean){  viewHolder.itemView.tag = isClamped}
    private fun getTag(viewHolder: RecyclerView.ViewHolder) : Boolean = viewHolder.itemView.tag as? Boolean ?: false
}