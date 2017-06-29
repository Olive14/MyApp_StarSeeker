package starseeker.com.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.util.Linkify;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class StarSeekerMain extends Activity implements LocationListener {
    private static final String SEPARATOR = "-";
    private LocationManager locationManager;
    private Map parameter = new HashMap();
    private List starInfoList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_star_seeker_main);

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            String locationProvider = null;
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            // GPSが利用可能になっているかどうかをチェック
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationProvider = LocationManager.GPS_PROVIDER;
            }
            // GPSプロバイダーが有効になっていない場合は基地局情報が利用可能になっているかをチェック
            else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER;
            }
            // いずれも利用可能でない場合は、GPSを設定する画面に遷移する
            else {
                Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(settingsIntent);
                return;
            }
            try {
                locationManager.requestLocationUpdates(locationProvider, 10, 10, this);
                Location location = locationManager.getLastKnownLocation(locationProvider);
                if (location != null) {
                    setCurrentLocation(location);
                } else {
                    if (null == location) {
                        // 現在地が取得できなかった場合，GPSで取得してみる
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                    if (null == location) {
                        // 現在地が取得できなかった場合，無線測位で取得してみる
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                    if (null != location) {
                        setCurrentLocation(location);
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }

            Button seekBtn = (Button) findViewById(R.id.seekBtn);
            seekBtn.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            setCurrentTime(parameter);
                            parameter.put(StarSeekerConst.EXECUTE_TYPE, StarSeekerConst.EXECITE_TYPE_FIND);
                            //ボタン押下時のパラメタをセット。
                            LocationTimeInfoHolder.getInstance().setParameter(parameter);
                            Map params = LocationTimeInfoHolder.getInstance().getParameter();

                            try {
                                StarSeekerAsync findAsync = new StarSeekerAsync(StarSeekerMain.this);
                                AsyncTask task = findAsync.execute(params);
                                starInfoList = (List) task.get();
                                System.out.println(starInfoList.size());
                                createSpinner(starInfoList);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
    }

    private void createSpinner(List starInfoList) {
            List starNameList = new ArrayList();
            for (int i = 0; i < starInfoList.size(); i++) {
                StarInfo info = (StarInfo) starInfoList.get(i);
                starNameList.add(info.getStarName());
            }
            ArrayAdapter adapter = new ArrayAdapter(
                    this, android.R.layout.simple_spinner_dropdown_item, starNameList
            );
            Spinner starListSpinner = (Spinner) findViewById(R.id.starList);
            starListSpinner.setAdapter(adapter);
            starListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    Spinner spinner = (Spinner) adapterView;
                    String selectedStar = (String) spinner.getSelectedItem();
                    Toast.makeText(StarSeekerMain.this, String.format(selectedStar + "  が選択されました"),
                            Toast.LENGTH_LONG).show();
                    Map locationTimeInfo = LocationTimeInfoHolder.getInstance().getParameter();
                    locationTimeInfo.put(StarSeekerConst.STAR_NAME, selectedStar);//コンボボックスで選んだ星座
                    locationTimeInfo.put(StarSeekerConst.EXECUTE_TYPE, StarSeekerConst.EXECUTE_TYPE_SEARCH);

                    try {
                        StarSeekerAsync findAsync = new StarSeekerAsync(StarSeekerMain.this);
                        AsyncTask task = findAsync.execute(locationTimeInfo);
                        List starInfoList = (List) task.get();
                        StarInfo selectedStarInfo = (StarInfo) starInfoList.get(0);    //searchの場合返ってくるリストには結果が1つしか入っていない.
                        setSelectedStarInfoContent(selectedStarInfo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // do nothing.
                }

                private void setSelectedStarInfoContent(StarInfo starInfo) {
                    setStarNameTextViewContent(starInfo);
                    setStarContentTextViewContent(starInfo);
                    setStarOriginTextViewContent(starInfo);
                    setStarImageTextViewContent(starInfo);
                    setStarImageView(starInfo);
                }

                /*
                * 項目ごとにビューを更新する
                * */
                private void setStarNameTextViewContent(StarInfo starInfo) {
                    TextView textViewStarName = (TextView)findViewById(R.id.starName);
                    textViewStarName.setText(starInfo.getStarName() + '\n');
                }

                private void setStarContentTextViewContent(StarInfo starInfo) {
                    TextView textViewContent = (TextView)findViewById(R.id.content);
                    textViewContent.setText(starInfo.getContent() + '\n');
                }

                private void setStarOriginTextViewContent(StarInfo starInfo) {
                    TextView textViewOrigin = (TextView)findViewById(R.id.origin);
                    textViewOrigin.setText(starInfo.getOrigin() + '\n');
                }

                private void setStarImageTextViewContent(StarInfo starInfo) {
                    TextView textViewStarImage = (TextView)findViewById(R.id.starImageURL);
                    textViewStarImage.setText(starInfo.getStarImageUrl());
                    Linkify.addLinks(textViewStarImage, Linkify.ALL);
                }

                private void setStarImageView(StarInfo starInfo) {
                    ImageView starImage = (ImageView)findViewById(R.id.starImage);
                    Bitmap bitmap = (Bitmap) starInfo.getStarImage();
                    if(null!=bitmap) {
                        starImage.setImageBitmap(bitmap);
                    }
                }
            });
    }

    private void setCurrentTime(Map parameter) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);        // 0 - 11
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        parameter.put(StarSeekerConst.DATE, formattedCurrentDate(parameter, year, month, day));
        parameter.put(StarSeekerConst.HOUR, hour);  //オートボクシング
        parameter.put(StarSeekerConst.MINUTE, minute);  //オートボクシング
    }

    private String formattedCurrentDate(Map parameter, int year, int month, int day) {
        StringBuilder formatString = new StringBuilder();
        formatString.append(year).append(this.SEPARATOR).append(month + 1).append(this.SEPARATOR).append(day);

        return formatString.toString();
    }

    private void setCurrentLocation(Location location) {
        parameter.put(StarSeekerConst.LAT, location.getLatitude());
        parameter.put(StarSeekerConst.LNG,  location.getLongitude());
    }

    @Override
    public void onLocationChanged(Location location) {
        setCurrentLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        // do nothing.
    }

    @Override
    public void onProviderEnabled(String s) {
        // do nothing.
    }

    @Override
    public void onProviderDisabled(String s) {
        // do nothing.
    }
}
