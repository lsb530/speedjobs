import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import axios from 'axios';
import { Order } from '../Styled';
import TagSelector from '../tag/TagSelector';
import TagShower from '../tag/TagShower';

export default function AnnouncementInfo({ onChange, form, setForm }) {
  // experience 태그
  const experienceTags = [
    { name: '경력무관', sort: -1 },
    { name: '신입', sort: 0 },
    { name: '경력 1년 이상', sort: 1 },
    { name: '경력 2년 이상', sort: 2 },
    { name: '경력 3년 이상', sort: 3 },
    { name: '경력 4년 이상', sort: 4 },
    { name: '경력 5년 이상', sort: 5 },
    { name: '경력 6년 이상', sort: 6 },
    { name: '경력 7년 이상', sort: 7 },
    { name: '경력 8년 이상', sort: 8 },
    { name: '경력 9년 이상', sort: 9 },
    { name: '경력 10년 이상', sort: 10 },
  ];
  const experienceHandler = (sort) => {
    setForm((p) => ({ ...p, experience: sort }));
  };

  // position 태그
  const positionTags = [
    { name: '정규직', sort: 'PERMANENT' },
    { name: '계약직', sort: 'TEMPORARY' },
  ];
  const positionHandler = (sort) => {
    setForm((p) => ({ ...p, position: sort }));
  };

  // 직무 태그
  const [click, setClick] = useState(false);
  const [taglist, setTaglist] = useState([]);
  const tagss = useSelector((state) => state.tag);
  useEffect(() => {
    if (tagss.tagGetData) {
      const temp = Array.from(tagss.tagGetData.tags.POSITION);
      const tempFromRecruit = [...(form.tags?.POSITION ?? []).map((t) => t.id)];
      const tt = temp.map((t) => {
        if (tempFromRecruit.indexOf(t.id) >= 0) return { ...t, selected: true };
        return { ...t, selected: false };
      });
      setTaglist((p) => [...p, ...tt]);
    }
  }, [tagss.tagGetData, form.tags?.POSITION]);
  useEffect(() => {
    if (click) {
      setForm((p) => {
        const ids = taglist.filter((t) => t.selected).map((t) => t.id);
        if (ids === []) {
          // eslint-disable-next-line
          return { ...p };
        } else {
          return { ...p, tagIds: [...ids] };
        }
      });
      setClick(!click);
    }
  }, [taglist, setForm, click]);

  // thumbnail 입력
  const [src, setSrc] = useState('');
  const dropHandler = async (e) => {
    e.preventDefault();
    if (e.dataTransfer.files.length !== 1) {
      alert('한개의 파일만 가능합니다.');
    } else {
      const f = e.dataTransfer.files[0];
      const data = new FormData();
      data.append('files', f);
      const url = await axios
        .post('/file', data)
        .then((res) => res.data.files[0].url)
        .catch(
          () =>
            'https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png'
        );
      if (
        f.type !== 'image/jpeg' &&
        f.type !== 'image/png' &&
        f.type !== 'image/gif'
      ) {
        alert('이미지 파일만 등록할 수 있습니다.');
      } else if (f.size > 1024 * 1024 * 10) {
        alert('10MB 이하 이미지만 가능합니다.');
      } else {
        setSrc(url);
      }
    }
  };
  useEffect(() => {
    if (src.length > 0) {
      setForm((p) => ({ ...p, thumbnail: src }));
    }
  }, [src, setForm]);
  const dragOver = (e) => {
    e.preventDefault();
  };
  const dragEnter = (e) => {
    e.preventDefault();
  };
  const dragLeave = (e) => {
    e.preventDefault();
  };

  return (
    <>
      <div>
        <div style={{ position: 'relative' }}>
          <div
            style={{ display: 'inline-block' }}
            onClick={() => setClick(true)}
          >
            <TagSelector tagList={taglist} setTagList={setTaglist}>
              직무
            </TagSelector>
          </div>
          <div
            style={{
              display: 'inline-block',
              height: '38px',
              verticalAlign: 'top',
            }}
          >
            <Order orderItem={positionTags} inOrder={positionHandler} />
          </div>
          <div
            style={{
              display: 'inline-block',
              height: '38px',
              verticalAlign: 'top',
            }}
          >
            <Order orderItem={experienceTags} inOrder={experienceHandler} />
          </div>
        </div>
        <div
          className={'text-left'}
          style={{ height: '50px', zIndex: '0' }}
          onClick={() => setClick(true)}
        >
          <TagShower tagList={taglist} setTagList={setTaglist} />
        </div>
        <textarea
          placeholder="공고 정보를 입력해주세요."
          name="content"
          style={{
            width: '100%',
            height: '200px',
            resize: 'none',
            border: '1px solid #a1a1a1',
            borderRadius: '15px',
            padding: '5px 10px',
            marginBottom: '10px',
          }}
          value={form?.content}
          onChange={(e) => onChange(e)}
        />

        <div
          style={{
            width: '100%',
            border: '#a1a1a1 1px solid',
            borderRadius: '15px',
            padding: '8px',
            textAlign: 'center',
            fontSize: 'small',
            backgroundColor: '#eee',
          }}
          onDragOver={dragOver}
          onDragEnter={dragEnter}
          onDragLeave={dragLeave}
          onDrop={dropHandler}
        >
          {src.length === 0 ? (
            '공고에 필요한 썸네일사진을 드래그해주세요.'
          ) : (
            <img
              src={src}
              alt={'hello'}
              style={{ height: '50px', display: 'inline-block' }}
            />
          )}
        </div>
      </div>
    </>
  );
}
