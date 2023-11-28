import Pagination from "react-bootstrap/Pagination";
import { useAtom } from "jotai";
import { PaginationData } from "../data/atom";

export const PaginationCommon = ({ page, setPage }) => {
  const [paginationData] = useAtom(PaginationData);

  return (
    <div style={{ display: "flex", justifyContent: "center" }}>
      <Pagination>
        <Pagination.Item>{"<"}</Pagination.Item>
        {paginationData.total !== 0 ? (
          Array.from(
            { length: paginationData.maxPage },
            (_, i) => i + paginationData.start
          ).map((item) => (
            <>
              <Pagination.Item
                active={item === page}
                activeLabel=""
                onClick={() => {
                  if (item !== page) setPage(item);
                }}
              >
                {item}
              </Pagination.Item>
            </>
          ))
        ) : (
          <Pagination.Item>1</Pagination.Item>
        )}
        <Pagination.Item>{">"}</Pagination.Item>
      </Pagination>
    </div>
  );
};
