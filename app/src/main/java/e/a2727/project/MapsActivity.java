package e.a2727.project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient; //현재 위치를 얻고, 정보를 제공하는 객체
    private int REQUEST_CODE_PERMISSIONS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map); //activity2 레이아웃의 Flagment가져옴
        mapFragment.getMapAsync(this); //구글맵이 준비가 되었을경우 onMapReady 호출됨

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap; //구글 맵 객체가 들어옴

        // Add a marker in Sydney and move the camera
        LatLng jbnu = new LatLng(35.8468552, 127.1296769);
        //LatLng는 위, 경도를 표현하는 객체. 구글 라이브러리 사용한것.
        mMap.addMarker(new MarkerOptions().position(jbnu).title("전북대학교")); //JBNU에 마커박고. "전북대학교" 띄움
        mMap.moveCamera(CameraUpdateFactory.newLatLng(jbnu)); //JBNU위치로 카메라를 이동
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f)); // 처음에 위치 찍을때 해당위치 줌 기능, 17.0f값 조절가능.


    }

    public void onLastLocationButtonClicked(View view) { //실제로 현재 위치를 얻는 코드
        //23마시멜로 이상 버전일경우, 아래 mFusedLocationClient를 사용하기 직전에 체크하는 기본 코드.
        //why? 위치정보를 가지고 오기 때문에, 위치정보에 관련된 정보에 대한 permission을 체크해야함.
        //사용했을때, 사용자가 사용 허락을 하는지.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_PERMISSIONS);
            //권한 요청 코드, 다이얼 로그를 띄움, 거기서 사용자가 승낙하는지 안하는지.
            return; //리턴 때문에 아래 코드까지 안내려감.
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            //권한을 받고 마지막 위치 정보를 가지고 왔을때(성공했을때). OnSuccessListener 동작
            @Override
            public void onSuccess(Location location) {
                if (location != null){ //Location이 Null일 수 도 있기때문에 체크가 필요함.
                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude()); //location에 위도, 경도가 들어가있음.
                    mMap.addMarker(new MarkerOptions() //맵에 표시를 하기위해서 마커 박음
                            .position(myLocation)
                            .title("나의 현재 위치"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation)); //현재 위치로 카메라 이동
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f)); //해당위치 줌 기능.
                }

            }
        });
    }

    @Override
    //사용자 요청 처리코드
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //위에서 권한요청에 대한 수락을 받았다면 (REQUST코드)가 왔다면 처리해야하는 작업이 필요.
        if (requestCode == REQUEST_CODE_PERMISSIONS) { //승인이 떨어졌는지 안하는지를 확인하는것.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
                //권한이 없을시 띄우는것.
            }
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(MapsActivity.this, MainActivity.class));
        finish();
    }

}
