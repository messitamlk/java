package leon.homework.Fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import leon.homework.Activities.ChineseHomework
import leon.homework.Adapter.SubjectAdapter
import leon.homework.AppContext
import leon.homework.Data.Const
import leon.homework.Data.SaveData
import leon.homework.JavaBean.Subject
import leon.homework.R
import org.jetbrains.anko.async
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.util.*




class WriteFragment : BaseFragment(),SwipeRefreshLayout.OnRefreshListener {
    val workreceiver = getWorkReceiver()
    companion object {
        fun newInstance(): WriteFragment {
            return WriteFragment()
        }
    }
    override val layoutResourceId: Int =R.layout.fragment_write
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBroadCast()
    }

    private var subjectList = ArrayList<Subject>()
    private var swiperefresh: SwipeRefreshLayout? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_write, container, false)
        val objAdapter = SubjectAdapter(activity, R.layout.works_list_item, subjectList)
        val listview = view.findViewById(R.id.list_view) as ListView
        subjectList.clear();
        swiperefresh = view.findViewById(R.id.SwipeRL_write) as SwipeRefreshLayout
        swiperefresh!!.setOnRefreshListener(this)
        listview.setOnItemClickListener({ parent, view, position, id ->
            val subject = subjectList[position]
            val context = activity
            if (subject.subjectName === "语文") {
                val intent = Intent(context, ChineseHomework::class.java)
                context.startActivity(intent)
            }
        })
        objAdapter.setNotifyOnChange(true)
        listview.adapter = objAdapter
        return view
    }
    fun addSubject(js:JSONObject){
        val subject = js.getString("subject")
        val img = getSubjectImg(js.getString("subject"))
        val deadline = js.getString("deadline")
        val worknum = js.getString("worknum")
        val work=Subject(subject,img,deadline,worknum)
        info("subjectList.add(work)")
        subjectList.add(work)
    }
    fun getSubjectImg(subject:String):Int{
        return when(subject){
            "语文"->{
                R.drawable.chinese
            }
            "英语"->{
                R.drawable.english
            }
            else -> {
                R.drawable.teacher //TODO set default
            }
        }
    }
    override fun onRefresh() {
        async() {
            val js =JSONObject()
            js.put("ACTION",Const.BROADCAST_HOMEWORK)
            js.put("subject","语文")
            js.put("deadline","2017-01-01")
            js.put("worknum","5")
            val msg:String = js.toString()
            val mintent = Intent()
            mintent.action = Const.BROADCAST_HOMEWORK // 设置你这个广播的action
            mintent.putExtra("content", msg)
            AppContext.instance!!.sendBroadcast(mintent)
            uiThread {
                swiperefresh!!.isRefreshing = false
            }
        }
    }

    fun registerBroadCast(){
        val intentFilter = IntentFilter()
        intentFilter.addAction(Const.BROADCAST_HOMEWORK)
        AppContext.instance!!.registerReceiver(workreceiver,intentFilter)
    }
    inner class getWorkReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val msg = intent.extras.getString("content")
            val js = JSONObject(msg)
            js.remove("ACTION")
            addSubject(js)
            Log.i("WriteRecevier", "接收到:" + msg)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppContext.instance!!.unregisterReceiver(workreceiver)
    }
}
